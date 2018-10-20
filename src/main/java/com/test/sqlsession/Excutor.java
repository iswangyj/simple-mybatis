package com.test.sqlsession;

/**
 * @author wyj
 * @date 2018/10/20
 */
public interface Excutor {
     <T> T query(String statement, Object parameter);
}
