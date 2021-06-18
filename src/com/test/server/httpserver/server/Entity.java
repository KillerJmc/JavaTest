package com.test.server.httpserver.server;

/*
 * <servlet>
 *     <servlet-name>login</servlet-name>
 *	   <servlet-class>com.com.jmc.httpserver.LoginServlet</servlet-class>
 * </servlet>
 */
 
public class Entity {
	private String name;
	private String clz;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}
}
