package com.test.apply.spring.test.service;


import com.test.apply.spring.test.pojo.User;

/**
 * @author Jmc
 */
public interface UserService {
    User getById(Integer id);
    void transfer(User from, User to, Double money);
}
