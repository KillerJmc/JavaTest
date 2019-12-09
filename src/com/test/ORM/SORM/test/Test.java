package com.test.ORM.SORM.test;

import com.test.ORM.SORM.core.Query;
import com.test.ORM.SORM.core.QueryFactory;
import com.test.ORM.SORM.core.TableContext;
import com.test.ORM.SORM.po.Dept;
import com.test.ORM.SORM.po.Emp;
import com.test.ORM.SORM.vo.EmpVo;

import java.io.*;
import java.util.List;

@SuppressWarnings("all")
public class Test {
    public static void main(String[] args) throws InterruptedException {
        //create po classes
        //TableContext.updateJavaPoFile();

    }

    private static void regularTest() {
        add();
        newLine();
        query();
        newLine();
        update();
        newLine();
        Query q = QueryFactory.createQuery();
        System.out.println(q.queryById(Emp.class, 100));
        newLine();
        delete();
        newLine();
        System.out.println(q.queryById(Emp.class, 100));
    }

    private static void newLine() {
        System.out.println("\n---------------------------------------------------------------------------------\n");
    }

    private static void add() {
        Emp e = new Emp();
        e.setName("Jmc");
        e.setAge(20);
        e.setSalary(6000.0);
        e.setId(100);

        Query q = QueryFactory.createQuery();
        q.insert(e);
    }

    private static void delete() {
        Emp e = new Emp();
        e.setName("Jmc");
        e.setId(100);

        Query q = QueryFactory.createQuery();
        q.delete(e);
    }

    private static void update() {
        Emp e = new Emp();
        e.setAge(180);
        e.setSalary(500.0);
        e.setId(100);
        Query q = QueryFactory.createQuery();
        q.update(e, "age", "salary");
    }

    private static void query() {
        Query q = QueryFactory.createQuery();
        List list = q.queryRows("select * from emp", Emp.class);
        list.forEach(System.out::println);
    }

    private static void select01() {
        Query q = QueryFactory.createQuery();
        Number num = q.queryNumber("select count(*) from emp where salary>?", 100);
        System.out.println(num);
    }

    private static void select02() {
        Query q = QueryFactory.createQuery();
        Emp e = (Emp) q.queryUniqueRow("select * from emp where id=?", Emp.class, 3);
        System.out.println(e);
    }

    private static void select03() {
        Query q = QueryFactory.createQuery();
        List list = q.queryRows("select * from emp where id>?", Emp.class, 3);
        list.forEach(System.out::println);
    }

    private static void select04() {
        Query q = QueryFactory.createQuery();
        var sql = "select e.id, e.name, salary+bonus 'totalSalary', age, d.name 'deptName', d.address 'deptAddr' from emp e\n" +
                "join dept d on e.deptId=d.id;";
        List list = q.queryRows(sql, EmpVo.class);
        list.forEach(System.out::println);
    }

    private static void select05() {
        Query q = QueryFactory.createQuery();
        Object e = q.queryById(Emp.class, 8);
        System.out.println(e);
    }

    /**
     * Run this fn first then run recover fn!
     */
    private static void createTables() {
        if (TableContext.tables.size() < 2) {
            Query q = QueryFactory.createQuery();
            //create tables
            q.executeDML("CREATE TABLE `emp` (\n" +
                    "  `id` int(10) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(20) DEFAULT NULL,\n" +
                    "  `salary` double DEFAULT NULL,\n" +
                    "  `birthday` date DEFAULT NULL,\n" +
                    "  `age` int(11) DEFAULT NULL,\n" +
                    "  `deptId` int(10) DEFAULT NULL,\n" +
                    "  `bonus` double(255,0) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

            q.executeDML("CREATE TABLE `dept` (\n" +
                    "  `id` int(10) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(30) DEFAULT NULL,\n" +
                    "  `address` varchar(100) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
        }
    }

    /**
     * Don't run this fn casually!
     */
    private static void backupTableFields() {
        Query q = QueryFactory.createQuery();
        List emps = q.queryRows("select * from emp", Emp.class);
        List depts = q.queryRows("select * from dept", Dept.class);

        try (var oos = new ObjectOutputStream(new FileOutputStream("./src/com/test/ORM/SORM/backup/tables.bak"))) {
            oos.writeObject(emps);
            oos.writeObject(depts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run createTables fn first then run this fn!
     */
    private static void recoverBackupTableFields() {
        Query q = QueryFactory.createQuery();
        List emps = null, depts = null;
        try (var ois = new ObjectInputStream(new FileInputStream("./src/com/test/ORM/SORM/backup/tables.bak"))) {
            emps = (List) ois.readObject();
            depts = (List) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        emps.forEach(q::insert);
        depts.forEach(q::insert);
    }

}

