package com.test;

import com.test.domain.User;
import com.test.mapper.UserMapper;
import com.test.sqlsession.SimpleSqlSession;

/**
 * @author wyj
 * @date 2018/10/20
 */
public class SimpleMyBatisTest {
    public static void main(String[] args) {
        SimpleSqlSession simpleSqlSession = new SimpleSqlSession();
        UserMapper userMapper = simpleSqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByPrimaryKey(1);
        System.out.println(user);
    }
}
