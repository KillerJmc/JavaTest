package com.test.server.chatserver;

import com.jmc.io.Files;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
* 功能:客户端接收
*
*/

public class Receive implements Runnable
{
	//输入流
	private DataInputStream in;
	//运行标识
	private boolean isRunning = true;
	//用户昵称
	private String name;
	//用户客户端
	private Socket socket;

	//构造方法
	public Receive(Socket socket, String name) {
		//用户信息
		this.name = name;
		this.socket = socket;
		//输入流
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			CloseUtils.closeAll(in);
			isRunning = false;
			System.out.println("创建流失败！\n");
		}
	}
	
	//读取
	public String receive() {
		//保存读取内容
		String content = null;
		try {
			//读取
			content = in.readUTF();
		} catch (IOException e) {
			CloseUtils.closeAll(in);
			isRunning = false;
		}
		//返回读取的内容
		return content;
	}
	
	//线程主体
	public void run() {
		while (isRunning) {
			String receive = receive();
			//若接收为空
			if(receive == null) {
				System.out.println("\n你已退出服务器\n");
				return;
			} else if (receive.equals("records")) {
				//读取本地记录
				try {
					System.out.println("\n-------------------------------------\n"
						 + Files.read("/sdcard/Client/" + name + "/Record")
						 + "-------------------------------------\n");
				} catch (Exception e) {}
				//跳过本次循环
				continue;
			}
			//输出读取内容
			System.out.println(receive + "\n");
			//记录聊天记录
			try {
				Files.out(receive + "\n\n", "/sdcard/Client/" + name + "/Record", true);
			} catch (Exception e){}
		}
	}
}
