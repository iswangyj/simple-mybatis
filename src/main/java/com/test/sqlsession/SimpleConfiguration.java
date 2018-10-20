package com.test.sqlsession;

import com.test.config.Function;
import com.test.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wyj
 * @date 2018/10/20
 */
public class SimpleConfiguration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occurs while evaling xml:" + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        for (Object item : node.elements("property")) {
            Element element = (Element) item;
            String value = getValue(element);
            String name = element.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> unknown name");
            }

            switch (name) {
                case "driverClassName":
                    driverClassName = value;
                    break;
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }
        Class.forName(driverClassName);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    @SuppressWarnings("rawTypes")
    public MapperBean readMapper(String path) {
        MapperBean mapperBean = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapperBean.setInterfaceName(root.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<>();
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                Function function = new Function();
                Element element = (Element) rootIter.next();
                String sqlType = element.getName().trim();
                String funcName = element.attributeValue("id").trim();
                String sql = element.getText().trim();
                String resultType = element.attributeValue("resultType").trim();
                function.setSqlType(sqlType);
                function.setFuncName(funcName);

                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (ClassNotFoundException e3) {
                    e3.printStackTrace();
                }

                function.setResultType(newInstance);
                function.setSql(sql);

                list.add(function);
            }

            mapperBean.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapperBean;
    }
}
