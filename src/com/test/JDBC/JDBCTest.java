package com.test.JDBC;

import com.jmc.chatserver.CloseUtils;
import com.sun.source.doctree.ThrowsTree;
import com.test.Main.Tools;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        //batch();
        select();
    }

    public static void connect() {
        //建立连接（禁用SSL） url格式：jdbc:mysql://host:post/database
        //这个连接比较耗时（利用Socket对象是远程连接，比较耗时，是管理的一个要点，为了提高效率，一般用连接池管理对象！）
        Tools.milliTimer(() -> {
            Connection conn = conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
            System.out.println(conn);
            System.out.println("建立连接");
            //记得关闭
            conn.close();
        });
    }

    /**
     * 安全性低
     *
     * @throws Exception
     */
    public static void statement() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
             Statement stm = conn.createStatement()) {
            String sql = "insert into t_user(name, pwd, regTime) values('赵六', 3444, now())";
            //不安全，可被SQL注入，如：
            String id = "5 or 1=1";
            sql = "delete from t_user where id=" + id;
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全性高
     *
     * @throws Exception
     */
    public static void preparedStatement() throws Exception {
        Connection conn = conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");

        //?占位符
        String sql = "insert into t_user(name, pwd) values(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        //参数索引从1开始算
        ps.setString(1, "搞起");
        ps.setString(2, "123456");
        ps.execute();
        ps.close();

        //直接用Object更方便
        sql = "insert into t_user(name, pwd, regTime) values(?, ?, ?)";
        ps = conn.prepareStatement(sql);

        ps.setObject(1, "张三");
        ps.setObject(2, "23873");
        ps.setObject(3, new java.sql.Date(System.currentTimeMillis()));
        //ps.execute();
        //运行insert/update/delete操作，返回更新行数
        int count = ps.executeUpdate();
        System.out.println(count);
        CloseUtils.closeAll(ps, conn);
    }

    public static void select() throws Exception {
        Connection conn = conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");

        //select id, name, pwd from t_user where id>?
        String sql = "select * from t_user where id>?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, 17);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "---" + rs.getString(2) + "---" + rs.getString(3));
        }
        CloseUtils.closeAll(rs, ps, conn);
    }

    /**
     * 批处理
     *
     * @throws Exception
     */
    public static void batch() {
        Tools.milliTimer(() -> {
            Connection conn = conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
            //设为手动提交
            conn.setAutoCommit(false);
            var stm = conn.createStatement();

            for (int i = 0; i < 20000; i++) {
                stm.addBatch("insert into t_user(name, pwd, regTime) values('gao" + i +"', 666666, now())");
            }
            stm.executeBatch();
            conn.commit();
            System.out.println("插入20000条数据");
        });
    }

}
