package com.test.mapper;

import com.test.domain.User;

/**
 * @author wyj
 * @date 2018/10/20
 */
public interface UserMapper {
    public User getUserByPrimaryKey(Integer id);
}
