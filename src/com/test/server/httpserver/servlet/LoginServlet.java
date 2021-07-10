package com.test.server.httpserver.servlet;

import com.test.server.httpserver.server.Request;
import com.test.server.httpserver.server.Response;
import com.test.server.httpserver.server.WebApp;

/**
 * 功能：登录
 */

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request req, Response reply) throws Exception {
		String name = req.getParameterValue("uname");
		String pwd = req.getParameterValue("pwd");
		if (null == name && null == pwd) {
			reply.println(
				new String(
					WebApp.getInputStream("com/test/server/httpserver/WEB_INFO/Login.html").readAllBytes()
				));
			return;
		}
		
		System.out.println("name：" + name);
		System.out.println("pwd：" + pwd);
		if (login(name, pwd)) {
			reply.println("登录成功");
		} else {
			reply.println("登录失败");
		}
	}
	
	public boolean login(String name, String pwd) {
		return name.equals("Jmc") && pwd.equals("12346");
	}

	@Override
	public void doPost(Request req, Response reply) throws Exception {
		
	}

}
