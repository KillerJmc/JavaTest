package com.test.mysql.sorm.pojo;

import lombok.Data;

@Data
public class Emp {
	private java.sql.Date birthday;
	private Double bonus;
	private String name;
	private Integer deptId;
	private Integer id;
	private Double salary;
	private Integer age;
}
