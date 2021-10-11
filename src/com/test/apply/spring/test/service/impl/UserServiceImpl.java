package com.test.apply.spring.test.service.impl;

import com.test.apply.spring.test.dao.UserDao;
import com.test.apply.spring.test.pojo.User;
import com.test.apply.spring.test.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * @author Jmc
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public void transfer(User from, User to, Double money) {
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        userDao.updateById(from);

        if (money % 2 == 1) {
            throw new RuntimeException();
        }

        userDao.updateById(to);
    }
}
