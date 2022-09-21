package com.test.server.httpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * 功能：http服务器
 * 作者：Jmc
 */

public class Server {
	private ServerSocket server;
	private boolean isShutDown = false;

	public static void main(String[] args)
    {
		new Server().start();
	}
	
	public Server() {
		new Thread(() -> {
			Scanner in = new Scanner(System.in);
			while (true) {
				if (in.next().equals("stop")) {
					stop();
					System.out.println("\n服务器已停止！\n");
					return;
				}
			}
		}).start();
	}
	
	//指定端口启动
	public void start(int port) {
		try {
			server = new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			stop();
		}
	}
	
	//默认启动
	public void start() {
		start(80);
	}
	
	//接收客户端
	public void receive() {
		try {
			while (!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//停止
	public void stop() {
		isShutDown = true;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
