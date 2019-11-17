package com.ORM.JuniorORM;

import java.sql.Date;

/**
 * 表和类对应
 */
public class Emp {
    private Integer id;
    private String name;
    private double salary;
    private Date birthday;
    private Integer age;
    private Integer deptId;

    public Emp(Integer id, String name, double salary, Date birthday, Integer age, Integer deptId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.birthday = birthday;
        this.age = age;
        this.deptId = deptId;
    }

    public Emp(String name, double salary, Integer age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public Emp() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String toStringAll() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                ", age=" + age +
                ", deptId=" + deptId +
                '}';
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
