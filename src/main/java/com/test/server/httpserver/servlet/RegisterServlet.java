package com.test.server.httpserver.servlet;

import com.test.server.httpserver.server.Request;
import com.test.server.httpserver.server.Response;

/**
 * 功能：注册
 */

public class RegisterServlet extends Servlet {

	@Override
	public void doGet(Request req, Response reply) throws Exception {
		
	}

	@Override
	public void doPost(Request req, Response reply) throws Exception {
		reply.println("<html><head><title>返回注册</title>");
		reply.println("</head><body>");
		reply.println("你的用户名为" + req.getParameterValue("uname"));
		reply.println("</body></html>");		
	}
}
