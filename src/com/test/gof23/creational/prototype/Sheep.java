package com.test.gof23.creational.prototype;

import java.sql.Date;

/**
 * 浅复制
 * @author Jmc
 *
 */

public class Sheep implements Cloneable {	//1997，英国克隆羊多利
	private String name;
	private Date birthday;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object obj = super.clone(); //直接调用
		return obj;
	}
	
	public Sheep(String name, Date date) {
		super();
		this.name = name;
		this.birthday = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
