package com.test.mysql.orm.sorm.vo;

public class EmpVo {
    private Integer id;
    private String name;
    private Double totalSalary;
    private Integer age;
    private String deptName;
    private String deptAddr;

    public EmpVo() {

    }

    public EmpVo(Integer id, String name, Double totalSalary, Integer age, String deptName, String deptAddr) {
        this.id = id;
        this.name = name;
        this.totalSalary = totalSalary;
        this.age = age;
        this.deptName = deptName;
        this.deptAddr = deptAddr;
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

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptAddr() {
        return deptAddr;
    }

    public void setDeptAddr(String deptAddr) {
        this.deptAddr = deptAddr;
    }

    @Override
    public String toString() {
        return "empVO{" +
                "id=" + id +
                ", name=" + name +
                ", totalSalary=" + totalSalary +
                ", age=" + age +
                ", deptName='" + deptName + '\'' +
                ", deptAddr='" + deptAddr + '\'' +
                '}';
    }
}
