package com.jmc.httpserver.servlet;

import com.jmc.httpserver.server.Request;
import com.jmc.httpserver.server.Response;

/**
 * 抽象为一个父类
 * 功能：服务器管理
 */

public abstract class Servlet {
	public void service(Request req, Response reply) throws Exception {
		this.doGet(req,reply);
		this.doPost(req,reply);
	}
	
	//只有子类可以访问
	protected abstract void doGet(Request req, Response reply) throws Exception;
	protected abstract void doPost(Request req, Response reply) throws Exception;
}
