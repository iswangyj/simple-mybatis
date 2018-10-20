package com.test.sqlsession;

import java.lang.reflect.Proxy;

/**
 * @author wyj
 * @date 2018/10/20
 */
public class SimpleSqlSession {
    private Excutor excutor = new SimpleExecutor();
    private SimpleConfiguration simpleConfiguration = new SimpleConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return excutor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new SimpleMapperProxy(this, simpleConfiguration));
    }
}
