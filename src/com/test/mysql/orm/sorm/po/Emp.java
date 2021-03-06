package com.test.mysql.orm.sorm.po;

import java.io.Serializable;

public class Emp implements Serializable {
	private java.sql.Date birthday;
	private Double bonus;
	private String name;
	private Integer deptId;
	private Integer id;
	private Double salary;
	private Integer age;

	public java.sql.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Emp{" +
				"birthday=" + birthday +
				", bonus=" + bonus +
				", name=" + name +
				", deptId=" + deptId +
				", id=" + id +
				", salary=" + salary +
				", age=" + age +
				'}';
	}
}
