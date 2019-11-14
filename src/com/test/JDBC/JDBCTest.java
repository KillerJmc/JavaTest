package com.test.JDBC;

import com.jmc.chatserver.CloseUtils;
import com.jmc.io.Streams;
import com.test.Main.Tools;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Objects;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        clear();
        blob();
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
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");

        //select id, name, pwd from t_user where id>?
        String sql = "select * from t_user where id>?";

        sql = "select * from t_user";
        PreparedStatement ps = conn.prepareStatement(sql);
        //ps.setObject(1, 17);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getInt(1) + " "
                + rs.getString(2) + " "
                + rs.getString(3) + " "
                + rs.getDate(4) + " "
                + rs.getTimestamp(5) + " ");

            //写rs.gerClob(6)也行
            Clob c = rs.getClob("description");
            if (c != null) {
                String clob = new String(Objects.requireNonNull(Streams.read(c.getAsciiStream())));
                System.out.print("clob（");
                if (clob.length() > 20) {
                    System.out.print(clob.substring(0, 20) + "...");
                } else {
                    System.out.print(clob);
                }
                System.out.print("）");
            }

            Blob b = rs.getBlob("headImg");
            if (b != null) {
                String blob = new String(Objects.requireNonNull(Streams.read(b.getBinaryStream())));
                System.out.print("blob（");
                if (blob.length() > 20) {
                    System.out.print(blob.substring(0, 20) + "...");
                } else {
                    System.out.print(blob);
                }
                System.out.print("）");
            }
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
            /**
             * 设为手动提交（把执行之前的所有语句当成一个事务，某条执行失败可回滚，不会写入数据库）
             * 而默认为自动递交，每条指令都是一个事务，不可回滚，都会尝试写入数据库
             */
            conn.setAutoCommit(false);
            //用Statement更保险，PreparedStatement可能会因为域空间问题出错
            var stm = conn.createStatement();

            for (int i = 0; i < 20000; i++) {
                stm.addBatch("insert into t_user(name, pwd, regTime) values('gao" + i +"', 666666, now())");
            }
            stm.executeBatch();
            conn.commit();
            System.out.println("插入20000条数据");

            CloseUtils.closeAll(stm, conn);
        });
    }

    /**
     * 事务：要么全部成功，要么全部失败。一个事务里有多条语句的时候，如果有执行失败的，会回滚到指令执行前的状态
     */
    public static void commit() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
             var ps1 = conn.prepareStatement("insert into t_user(name, pwd) value(?, ?)");
             //ps2执行失败会自动回滚
             var ps2 = conn.prepareStatement("insert into t_user(name, pwd) value(?, ?, ?)")) {
            conn.setAutoCommit(false);

            ps1.setObject(1, "gaoqi");
            ps1.setObject(2, "123456");
            ps1.execute();
            System.out.println("新建用户：gaoqi");

            Tools.sleep(3000);

            ps2.setObject(1, "马士兵");
            ps2.setObject(2, "123456");
            ps2.execute();
            System.out.println("新建用户：马士兵");

            //提交
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void date() {
        JDBCUtil.exec("insert into t_user(name, pwd, regTime, lastLoginTime) value(?, ?, ?, ?)",
            Arrays.asList(
                    "gaoqi",
                    "123456",
                    new java.sql.Date(System.currentTimeMillis()),
                    new java.sql.Timestamp((long)(System.currentTimeMillis() * 1.3))
            )
        );
        JDBCUtil.close();
    }

    public static void moreDates() {
        for (int i = 0; i < 1000; i++) {
            long randTime = (long) (System.currentTimeMillis() * Math.random());
            int finalI = i;
            JDBCUtil.exec("insert into t_user(name, pwd, regTime, lastLoginTime) value(?, ?, ?, ?)",
                Arrays.asList(
                        "gaoqi" + (finalI + 1),
                        "123456",
                        new java.sql.Date(randTime),
                        new java.sql.Timestamp(randTime)
                )
            );
        }
        JDBCUtil.close();
    }

    /**
     * 查询日期
     */
    public static void enquireDates() {

        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456")) {
            var ps = conn.prepareStatement("select * from t_user where regTime > ? and regTime < ?");
            var start = new java.sql.Date(LocalDateTime.of(2018, 1, 8, 10, 23,35).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            var end = new java.sql.Date(LocalDateTime.of(2018, 6, 18, 10, 23,35).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

            ps.setObject(1, start);
            ps.setObject(2, end);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " "
                        + rs.getString(2) + " "
                        + rs.getString(3) + " "
                        + rs.getDate(4) + " "
                        + rs.getTimestamp(5));
            }

            Tools.shortNewLine();

            var ps2 = conn.prepareStatement("select * from t_user where lastLoginTime > ? and lastLoginTime < ? order by lastLoginTime");
            var start2 = new Timestamp(LocalDateTime.of(2008, 1, 8, 10, 23,35).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            var end2 = new Timestamp(LocalDateTime.of(2008, 6, 18, 10, 23,35).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

            ps2.setObject(1, start2);
            ps2.setObject(2, end2);

            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                System.out.println(rs2.getInt(1) + " "
                        + rs2.getString(2) + " "
                        + rs2.getString(3) + " "
                        + rs2.getDate(4) + " "
                        + rs2.getTimestamp(5));
            }

            //关闭
            CloseUtils.closeAll(rs2, ps2, rs, ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * clob（Character Large Object）：储存大量文本信息
     */
    public static void clob() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");

        var ps = conn.prepareStatement("insert into t_user(name, description) values(?, ?)");
        ps.setString(1, "gaoqi");
        //大数据！类型为text（文本）
        //ps.setClob(2, new FileReader(Tools.getJavaFilePath(JDBCTest.class)));

        //神奇的方法
        ps.setClob(2, new InputStreamReader(new ByteArrayInputStream("Hello World!".getBytes())));
        ps.executeUpdate();

        CloseUtils.closeAll(ps, conn);
    }

    /**
     * blob（Binary Large Object）：储存大量二进制信息
     */
    public static void blob() throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");

        var ps = conn.prepareStatement("insert into t_user(name, headImg) values(?, ?)");
        ps.setString(1, "gaoqi");
        ps.setBlob(2, new FileInputStream("./temp/head.jpg"));
        ps.executeUpdate();

        CloseUtils.closeAll(ps, conn);
    }

    public static void clear() {
        JDBCUtil.simpleExec("truncate t_user");
    }
}


