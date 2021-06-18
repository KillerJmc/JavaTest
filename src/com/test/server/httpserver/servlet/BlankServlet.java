package com.test.server.httpserver.servlet;

import com.test.server.httpserver.server.Request;
import com.test.server.httpserver.server.Response;

/**
 * 功能：主页
 */

public class BlankServlet extends Servlet
{

	@Override
	protected void doGet(Request req, Response reply) throws Exception {
		reply.println("欢迎来到主页");
	}

	@Override
	protected void doPost(Request req, Response reply) throws Exception {

	}
	
	
}
