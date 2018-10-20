package com.test.sqlsession;

import com.test.config.Function;
import com.test.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wyj
 * @date 2018/10/20
 */
public class SimpleMapperProxy implements InvocationHandler {
    private SimpleSqlSession simpleSqlSession;
    private SimpleConfiguration simpleConfiguration;

    public SimpleMapperProxy(SimpleSqlSession simpleSqlSession, SimpleConfiguration simpleConfiguration) {
        this.simpleSqlSession = simpleSqlSession;
        this.simpleConfiguration = simpleConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper = simpleConfiguration.readMapper("UserMapper.xml");
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }
        List<Function> list = readMapper.getList();
        if (null != list || 0 != list.size()) {
            for (Function func : list) {
                if (method.getName().equals(func.getFuncName())) {
                    return simpleSqlSession.selectOne(func.getSql(),String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}
