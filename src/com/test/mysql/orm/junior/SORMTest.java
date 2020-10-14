/**
 * 什么是ORM？
 * 即Object-Relational Mapping，
 * 它的作用是在关系型数据库和对象之间作一个映射，
 * 这样，我们在具体的操作数据库的时候，就不需要再去和复杂的SQL语句打交道，
 * 只要像平时操作对象一样操作它就可以了 。
 */
package com.test.mysql.orm.junior;

import com.test.mysql.jdbc.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SORMTest {
    private static final JDBCUtil jdbcUtil = new JDBCUtil(SORMTest.class);

    public static void main(String[] args) throws SQLException {
        pushToObjs();
        pushToMap();
        pushToDoubleMap();
        useJavaBean();
    }

    public static void pushToObjs() throws SQLException {
        List<Object[]> list = new ArrayList<>();

        ResultSet rs = jdbcUtil.getResultSet("select name, salary, age from emp where id > ?", 1);
        while(rs.next()) {
            var objs = new Object[3];
            for (int i = 0; i < 3; i++) objs[i] = rs.getObject(i + 1);
            list.add(objs);
        }

        list.stream()
            .map(Arrays::toString)
            .forEach(System.out::println);
    }

    public static void pushToMap() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();

        ResultSet rs = jdbcUtil.getResultSet("select name, salary, age from emp where id > ?", 1);
        while(rs.next()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", rs.getObject(1));
            map.put("salary", rs.getObject(2));
            map.put("age", rs.getObject(3));

            list.add(map);
        }

        System.out.println(list);
    }

    /**
     * 按名字存储
     * @throws SQLException
     */
    public static void pushToDoubleMap() throws SQLException {
        Map<String, Map<String, Object>> map = new HashMap<>();

        ResultSet rs = jdbcUtil.getResultSet("select name, salary, age from emp where id > ?", 1);
        while(rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("name", rs.getObject(1));
            row.put("salary", rs.getObject(2));
            row.put("age", rs.getObject(3));

            //以真实名字作为key
            map.put(rs.getString(1), row);
        }

        System.out.println(map);
    }

    /**
     * JavaBean是一种可重用的Java组件，
     * 它可以被Applet、Servlet、SP等Java应用程序调用，
     * 也可以可视化地被Java开发工具使用。
     * 它包含属性(Properties)、方法(Methods)、事件(Events)等特性。
     * @throws SQLException
     */
    public static void useJavaBean() throws SQLException {
        ResultSet rs = jdbcUtil.getResultSet("select name, salary, age from emp where id > ?", 1);
        List<Emp> list = new ArrayList<>();

        while(rs.next()) {
            var emp = new Emp(rs.getString(1), rs.getDouble(2), rs.getInt(3));
            list.add(emp);
        }

        System.out.println(list);
    }
}
