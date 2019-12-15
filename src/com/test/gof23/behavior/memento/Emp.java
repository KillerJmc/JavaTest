package com.test.gof23.behavior.memento;

/**
 * 源发器类（即将被复制的类）
 */
public class Emp {
    private String ename;
    private int age;
    private double salary;

    /**
     * 进行备忘操作，并返回备忘录对象
     */

    public EmpMemento memento() {
        return new EmpMemento(this);
    }

    /**
     * 进行数据恢复，恢复成制定备忘录的值
     */
    public void recovery(EmpMemento emt) {
        this.ename = emt.getEname();
        this.age = emt.getAge();
        this.salary = emt.getSalary();
    }

    public Emp(String ename, int age, double salary) {
        this.ename = ename;
        this.age = age;
        this.salary = salary;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
