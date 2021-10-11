package com.test.apply.spring.test.main;

import com.test.apply.spring.ioc.core.ClassPathApplicationContext;
import com.test.apply.spring.test.controller.UserController;
import com.test.apply.spring.test.util.UserDatabase;

public class Main {
    public static final String CONFIG_CLASS_PATH = "com/test/apply/spring/test/resource/applicationContext.xml";

    public static void main(String[] args) {
        var app = new ClassPathApplicationContext(CONFIG_CLASS_PATH);
        var userController = app.getBean(UserController.class);
        userController.transfer(123d);

        // 数据库信息展示
        UserDatabase.showUsers();
    }
}
