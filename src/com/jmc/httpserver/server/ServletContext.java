package com.jmc.httpserver.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：上下文
 */

public class ServletContext {
	//为每一份servlet取个别名
	//sevlet存的是包名(Map:servlet的第二个参数)
	//可以保证性能，减少内存消耗
	private Map<String, String> servlet;
	private Map<String, String> mapping;
	
	public ServletContext() {
		servlet = new HashMap<String, String>();
		mapping = new HashMap<String, String>();
	}
	
	public Map<String, String> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
}
