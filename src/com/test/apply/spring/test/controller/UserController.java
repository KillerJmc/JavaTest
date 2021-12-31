package com.test.apply.spring.test.controller;

import com.test.apply.spring.test.pojo.User;
import com.test.apply.spring.test.service.UserService;
import com.test.apply.spring.test.util.TransactionAop;
import lombok.RequiredArgsConstructor;

/**
 * @author Jmc
 */
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 转账方法
     * @param money 金额
     */
    public void transfer(Double money) {
        User from = userService.getById(1), to = userService.getById(2);
        System.out.printf("%s 正在向 %s 转账 %.0f 元\n", from.getName(), to.getName(), money);

        // 获取代理对象实现aop
        var userServiceProxy =
                TransactionAop.getProxyWithRollbackEnhancement(userService, "transfer");
        userServiceProxy.transfer(from, to, money);
    }
}
