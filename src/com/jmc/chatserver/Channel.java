package com.jmc.chatserver;

import java.io.*;
import java.net.*;
import java.util.*;
import com.jmc.io.*;

/**
* 功能: 服务器与客户端连接的通道(仅限服务器调用)
*
*/

public class Channel implements Runnable
 {
	//输入输出流
	private DataInputStream in;
	private DataOutputStream out;
	//运行标识
	private boolean isRunning = true;	
	//用户昵称
	public String name;
	//是否被踢
	public boolean beKicked = false;
	//集合获取
	private static List<Channel> list;
	//数据库路径
	private final String dataPath = "/sdcard/Server/DataBase";

	//构造方法
	public Channel(Socket socket, String name) {
		//记录用户昵称
		this.name = name;
		//获取集合
		list = Server.list;
		
		try {			
			//输入输出流
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			exit();
			System.out.println("创建流失败！\n");
		}
	}

	//接收
	public String receive() {
		//保存读取内容
		String content = null;
		try {
			//读取
			content = in.readUTF();
		} catch (Exception e) {
			isRunning = false;
			CloseUtils.closeAll(in,out);
		}		
		//返回读取的内容
		return content;
	}

	//发送
	public void send(String content) {
		try {
			//内容不合法就退出
			if(content == null | content.equals("")) {
				return;
			}
			//写入
			out.writeUTF(content);
		} catch (Exception e) {
			isRunning = false;
			CloseUtils.closeAll(in,out);
		}
	}

	//发送给指定人(服务器)
	public static void sendToSomeone(String userName, String content) {
		//判断参数是否为空
		if(userName == null | content == null) return;

		//遍历集合
		for (Channel user : list) {
			//如果找到对方
			if (user.name.equals(userName)) {
				user.send(content);
				break;
			}
		}
	}

	//发送给其他人
	public void sendToOthers() throws Exception {		
		//用户发送内容
		String msg = receive();		
		//判断信息是否为空
		if(msg == null) return;

		//发起私聊的人
		String whoSend = null;
		//对方的昵称
		String theOtherSide = null;

		//是否为私聊
		if (msg.contains("@") & msg.contains(":")) {
			//格式: @name: content
			whoSend = msg.substring(0,msg.indexOf("@"));
			theOtherSide = msg.substring(msg.indexOf("@") + 1, msg.indexOf(":"));
			msg = whoSend + "对你悄悄地说: "
				+ msg.substring(msg.indexOf(":") + 2);
				
			//发送
			sendToSomeone(theOtherSide,msg);
			
			//系统输出
			msg = "\n" + whoSend + "对" 
				+ theOtherSide + "悄悄地说: " 
				+ msg.substring(msg.indexOf(":") + 2) + "\n";		
			  	
			System.out.println(msg);		
			
			//输出到数据库
			Files.out((msg + "\n"),(dataPath + "/Record"),true);
			//返回
			return;
		} else if(msg.equals("help")) {
			send("\n\n" + "@user: content    私发给某用户"
				 + "\n" + "users    列出所有用户"
				 + "\n" + "records    列出聊天记录"
				 + "\n" + "exit    退出服务器");
			//返回
			return;
		} else if(msg.equals("users")) {
			String users = "";
			//遍历集合
			for (Channel user : list) {
				users += user.name + ", ";			
			}
			send("\n\n" + users.substring(0,users.length() - 2));
			//返回
			return;
		} else if(msg.equals("records")) {
			send("records");
			//返回
			return;
		} else if (msg.contains("exit")) {	
			exit();
			return;
		} else {
			msg = name + ": " + msg;
		}

		//遍历集合
		for (Channel user : list) {				
			//不发给自己
			if(user == this) continue;

			//发送给用户
			user.send(msg);	
		}	
		
		//发送给服务器
		System.out.println(msg + "\n");
		
		//输出到数据库
		Files.out((msg + "\n\n"),(dataPath + "/Record"),true);
	}
	
	//群发
	public void sendToOthers(String content) {
		//遍历集合
		for (Channel user : list) {
			//不发给自己
			if(user.name.equals(name)) continue;

			//发送给用户
			user.send(content);
		}
	}

	//发给所有人(服务器)
	public static void sendToAll(String content) {
		//判断参数是否为空
		if(content == null) return;

		//遍历集合
		for (Channel user : list) {
			user.send(content);
		}
	}

	//离开提示信息
	public void exit() {
		if (!beKicked) {
			System.out.println("\n" + name + "已退出服务器\n");
			//输出到数据库
			try {
				Files.out(("\n" + name + "已退出服务器\n\n"),(dataPath + "/Record"),true);
			} catch(Exception e) {}
			
			sendToOthers(name + "离开了聊天室\n");
		} else {
			System.out.println("\n" + name + "已被踢出服务器\n");
			//输出到数据库
			try {
				Files.out(("\n" + name + "已被踢出服务器\n\n"),(dataPath + "/Record"),true);
			} catch(Exception e) {}
			sendToOthers(name + "被踢出了服务器\n");			
			sendToSomeone(name,"你已被踢出服务器！");
		}
		isRunning = false;
		CloseUtils.closeAll(in, out);
		list.remove(this);
	}

	//线程主体
	public void run() {
		while (isRunning) {
			//持续同步更新
			try {
				sendToOthers();
			} catch(Exception e) {}
		}
	}
}
