package com.test.server.httpserver.server;


import com.test.server.httpserver.servlet.Servlet;

import java.io.IOException;
import java.net.Socket;

/**
 * 功能：调度（分发）
 */

public class Dispatcher implements Runnable{
	private Socket client;
	private Request req;
	private Response rep;
	private int code = 200;
	
	public Dispatcher(Socket client) {
		this.client = client;
		try {
			req = new Request(client.getInputStream());
			rep = new Response(client.getOutputStream(), true);
		} catch (IOException e) {
			code = 500;
		}
	}
	
	@Override
	public void run() {
		try {
			Servlet serv = WebApp.getServlet(req.getUrl());
			if (null == serv) {
				//找不到资源
				this.code = 404;
			} else {
				serv.service(req, rep);
			}
		
			//推送到客户端
			rep.pushToClient(code);
		} catch (Exception e) {
			//系统内部错误
			this.code = 500;
		}

		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
