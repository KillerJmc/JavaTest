package com.test.apply.spring.test.dao.impl;

import com.test.apply.spring.test.dao.UserDao;
import com.test.apply.spring.test.pojo.User;
import com.test.apply.spring.test.util.UserDatabase;

/**
 * @author Jmc
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User getById(Integer id) {
        // 从事务中获取
        return UserDatabase.getByIdFromTrx(id);
    }

    @Override
    public void updateById(User user) {
        UserDatabase.updateById(user);
    }
}
