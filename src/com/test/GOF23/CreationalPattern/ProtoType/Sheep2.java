package com.test.GOF23.CreationalPattern.ProtoType;

/**
 * 深复制
 */

import java.sql.Date;

import com.test.Main.Tools;

public class Sheep2 implements Cloneable {	//1997，英国克隆羊多利
	private String name;
	private Date birthday;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object obj = super.clone(); //直接调用
		
		Sheep2 s = (Sheep2) obj;
		//对属性也进行克隆
		s.setBirthday((Date) this.birthday.clone());
		
		return obj;
	}
	
	public Sheep2(String name, Date date) {
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
