package com.test.mysql.sorm.main;

import com.jmc.lang.Outs;
import com.test.mysql.sorm.pojo.Emp;
import com.test.mysql.sorm.query.Query;
import com.test.mysql.sorm.query.QueryFactory;

import java.util.List;

public class Test {
    private static final Query Q = QueryFactory.createQuery();
    
    public static void main(String[] args) throws InterruptedException {
        regularTest();
    }

    private static void regularTest() {
        Outs.newLine(Test::add);
        Outs.newLine(Test::query);
        Outs.newLine(Test::update);
        
        System.out.println(Q.queryById(Emp.class, 100));
        Outs.newLine(Test::delete);
        System.out.println(Q.queryById(Emp.class, 100));
    }

    private static void add() {
        Emp e = new Emp();
        e.setName("Jmc");
        e.setAge(20);
        e.setSalary(6000.0);
        e.setId(100);

        Q.insert(e);
    }

    private static void delete() {
        Emp e = new Emp();
        e.setName("Jmc");
        e.setId(100);

        Q.delete(e);
    }

    private static void update() {
        Emp e = new Emp();
        e.setAge(180);
        e.setSalary(500.0);
        e.setId(100);
        
        Q.update(e, "age", "salary");
    }

    private static void query() {
        List<Emp> list = Q.queryRows("select * from emp", Emp.class);
        list.forEach(System.out::println);
    }

    private static void select01() {
        Number num = Q.queryNumber("select count(*) from emp where salary>?", 100);
        System.out.println(num);
    }

    private static void select02() {
        Emp e = (Emp) Q.queryUniqueRow("select * from emp where id=?", Emp.class, 3);
        System.out.println(e);
    }

    private static void select03() {
        List<Emp> list = Q.queryRows("select * from emp where id>?", Emp.class, 3);
        list.forEach(System.out::println);
    }

    private static void select05() {
        Object e = Q.queryById(Emp.class, 8);
        System.out.println(e);
    }
}

