package com.test.apply.spring.test.dao;

import com.test.apply.spring.test.pojo.User;

/**
 * @author Jmc
 */
public interface UserDao {
    User getById(Integer id);
    void updateById(User u);
}
