package com.test.gof23.creational.prototype;

import java.io.*;
import java.sql.Date;

public class Sheep3 implements Serializable {	//1997，英国克隆羊多利
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date birthday;
	
	public Object deepClone() throws Exception {
		var bos = new ByteArrayOutputStream();
		try (var oos = new ObjectOutputStream(bos)) {
			oos.writeObject(this);
			oos.flush();
		}
		
		try (var bis = new ByteArrayInputStream(bos.toByteArray());
			 var ois = new ObjectInputStream(bis)) {
			return ois.readObject();
		}
	}
	
	public Sheep3(String name, Date date) {
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
