package com.test.mysql.orm.sorm.po;

import java.io.Serializable;

public class Dept implements Serializable {
	private String address;
	private String name;
	private Integer id;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Dept{" +
				"address=" + address +
				", name=" + name +
				", id=" + id +
				'}';
	}
}
