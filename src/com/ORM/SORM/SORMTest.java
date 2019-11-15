package com.ORM.SORM;

import com.test.JDBC.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SORMTest {
    private static JDBCUtil jdbcUtil = new JDBCUtil(SORMTest.class);
    private static Connection conn = jdbcUtil.getMySQLConn();

    public static void main(String[] args) throws SQLException {
        pushToObjs();
        pushToMap();
        pushToDoubleMap();
    }

    public static void pushToObjs() throws SQLException {
        List<Object[]> list = new ArrayList<>();

        ResultSet rs = jdbcUtil.getResultSet("select * from emp where id > ?", 1);
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

        ResultSet rs = jdbcUtil.getResultSet("select * from emp where id > ?", 1);
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
     * 按行存储
     * @throws SQLException
     */
    public static void pushToDoubleMap() throws SQLException {
        Map<String, Map<String, Object>> map = new HashMap<>();

        ResultSet rs = jdbcUtil.getResultSet("select * from emp where id > ?", 1);
        while(rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("name", rs.getObject(1));
            row.put("salary", rs.getObject(2));
            row.put("age", rs.getObject(3));

            map.put(rs.getString(1), row);
        }

        System.out.println(map);
    }
}
