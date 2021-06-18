package com.test.server.httpserver.server;

import java.io.File;
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
	public static String projectPath;
	
	public static void main(String[] args)
    {
		new Server().start();
	}
	
	public Server() {	
		//判断是否为安卓设备
		if (new File("/sdcard/").exists()) {
			System.out.println("\n您正在使用安卓设备，请输入工程的绝对路径：\n");
			this.projectPath = new Scanner(System.in).next();
			if (!new File(this.projectPath).exists()) {
				System.out.println("\n工程路径不存在！\n");
				stop();
				return;
			} else {
				System.out.println("\n成功！\n");
			}
		}
		
		new Thread(new Runnable() {
			public void run() {
				Scanner in = new Scanner(System.in);
				while (true) {
					if (in.next().equals("stop")) {
						stop();
						System.out.println("\n服务器已停止！\n");
						return;
					}
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
			return;
		}
	}
	
	//默认启动
	public void start() {
		start(8888);
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
