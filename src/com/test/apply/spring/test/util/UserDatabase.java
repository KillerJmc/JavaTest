package com.test.apply.spring.test.util;

import com.test.apply.spring.test.pojo.User;

/**
 * 模拟数据库类
 * @author Jmc
 */
public class UserDatabase {
    /**
     * 数据库中对象
     */
    private static final User u = new User() {{ setId(1); setName("Jmc"); setAge(21); setMoney(1000.0); }};
    private static final User u2 = new User() {{ setId(2); setName("Jerry"); setAge(40); setMoney(1000.0); }};

    /**
     * 事务中对象
     */
    private static User copyU = cloneUser(u);
    private static User copyU2 = cloneUser(u2);

    /**
     * 克隆User对象
     */
    private static User cloneUser(User u) {
        return new User() {{ setId(u.getId()); setName(u.getName()); setAge(u.getAge()); setMoney(u.getMoney()); }};
    }

    /**
     * 模拟数据库提交事务
     */
    public static void commit() {
        u.setName(copyU.getName());
        u.setAge(copyU.getAge());
        u.setMoney(copyU.getMoney());

        u2.setName(copyU2.getName());
        u2.setAge(copyU2.getAge());
        u2.setMoney(copyU2.getMoney());

        System.err.println("\n----------------------------事务已提交！----------------------------");
    }

    /**
     * 模拟数据库回滚事务
     */
    public static void rollback() {
        copyU = cloneUser(u);
        copyU2 = cloneUser(u2);

        System.err.println("\n----------------------------事务已回滚！----------------------------");
    }

    /**
     * 模拟数据库获取用户对象（从事务中，仅支持一个事务）
     * @param id 用户id
     * @return 结果对象
     */
    public static User getByIdFromTrx(Integer id) {
        return id == 1 ? cloneUser(copyU) : id == 2 ? cloneUser(copyU2) : null;
    }

    /**
     * 模拟数据库获取用户对象（从数据库中）
     * @param id 用户id
     * @return 结果对象
     */
    public static User getByIdFromDataBase(Integer id) {
        return id == 1 ? cloneUser(u) : id == 2 ? cloneUser(u2) : null;
    }

    /**
     * 模拟数据库更新用户对象（从事务中）
     * @param user 更新的对象
     */
    public static void updateById(User user) {
        switch (user.getId()) {
            case 1 -> copyU = cloneUser(user);
            case 2 -> copyU2 = cloneUser(user);
            default -> {}
        }
    }

    /**
     * 获取用户信息
     */
    public static void showUsers() {
        User fromAfter = getByIdFromTrx(1), toAfter = getByIdFromTrx(2);
        System.out.printf("事务中 %s 剩余钱数 %.0f 元\n", fromAfter.getName(), fromAfter.getMoney());
        System.out.printf("事务中 %s 剩余钱数 %.0f 元\n", toAfter.getName(), toAfter.getMoney());

        fromAfter = getByIdFromDataBase(1);
        toAfter = getByIdFromDataBase(2);
        System.err.printf("数据库中 %s 剩余钱数 %.0f 元\n", fromAfter.getName(), fromAfter.getMoney());
        System.err.printf("数据库中 %s 剩余钱数 %.0f 元\n", toAfter.getName(), toAfter.getMoney());
    }
}
