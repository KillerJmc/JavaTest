package com.jmc.chatserver;

import java.io.*;
import java.net.*;
import com.jmc.io.*;

/**
* 功能: 客户端发送
*
*/

public class Send implements Runnable
{
	//用户输入流
	private BufferedReader in;
	//输出流
	private DataOutputStream out;
	//运行标识
	private boolean isRunning = true;
	//用户昵称
	private String name;
	//用户客户端
	private Socket socket;
	
	//构造方法
	public Send(Socket socket, String name) {
		//用户信息
		this.name = name;
		this.socket = socket;
		//输入输出流
		try {
			in = new BufferedReader(
				new InputStreamReader(System.in)
			);
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			CloseUtils.closeAll(in,out);
			isRunning = false;		
			System.out.println("创建流失败！\n");
		}
	}
	
	//发送
	public String send() {
		//用户输入内容
		String content = null;
		try {
			//读取和写入
			content = in.readLine();
			
			//若为私聊
			if (content.startsWith("@") & content.contains(":")) {
				//加上名字(用于识别)
				content = name + content;
			}
			
			out.writeUTF(content);
			
			//记录聊天记录
			try {
				Files.out((name + ": " + content + "\n\n"), ("/sdcard/Client/" + name + "/Record"), true);
			} catch (Exception e){}
			
			//打印空行
			System.out.println();
		} catch (Exception e) {
			CloseUtils.closeAll(in,out);
			isRunning = false;
		}
		return content;
	}
	
	//线程主体
	public void run() {
		while (isRunning) {
			//持续发送
			send();
		}
	}
}
