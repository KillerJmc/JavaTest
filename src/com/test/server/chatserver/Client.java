package com.test.server.chatserver;

import com.jmc.console.Input;
import com.jmc.exception.ExceptionHandler;
import com.jmc.io.Files;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

/**
* 功能: 客户端
*
*/

public class Client
{
	//连接服务器
	private static Socket client;
	//发送接收对象
	public static Send send;
	public static Receive receive;
	//获取用户输入
	private static Scanner in;
	//用户选择
	private static int select;
	//用户昵称
	private static String name;
	//用户账号
	private static String account;
	//用户密码
	private static String password;
	private static String password2;
	//管道
	private static Channel channel;
	//登录返回信息
	private static String loginMsg = "";
	
	
	public static void main(String[] args) 
	{
		try {
			init();
			login();
			
			if(loginMsg.endsWith("错误")) {
				System.out.println("\n进入聊天室失败\n");
				return;
			}
			//开启发送和接收线程
			send = new Send(client,name);
			receive = new Receive(client,name);
			
			new Thread(send).start();
			new Thread(receive).start();
			
			//输出时间到数据库
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
				Files.out(("----------------------------\n"
					+ sdf.format(new Date())
					+ "\n\n"),("/sdcard/Client/" + name + "/Record"),true
				);
			} catch(Exception e) {
				System.out.println(ExceptionHandler.showCaused((e)));
			}
		} catch (Exception e) {
			System.out.println(ExceptionHandler.showCaused(e));
		}
	}
	
	//初始化
	public static void init() throws Exception {
		//连接服务器
		client = new Socket("127.0.0.1", 8888);
	}
	
	//注册和登录
	public static void login() {
		//初始化Scanner
		in = new Scanner(System.in);
		//初始化管道
		channel = new Channel(client,null);
		
		//功能选择
		System.out.println("\n请选择登录方式:\n\n" + "1.账号注册   2.账号登录   3.游客登录\n");
		
		try {
			//获取用户选择
			select = in.nextInt();		
			//发送给客户端
			channel.send(String.valueOf(select));
		} catch(Exception e) {
			System.out.println("\n非法输入！\n");
			login();
			return;
		}
		
		//对其进行判断
		switch (select) {
			case 1: 
				System.out.print("\n请输入注册的昵称: ");
				name = in.next();
				channel.send(name);
				
				System.out.print("\n请输入注册的账号: ");
				account = in.next();
				channel.send(account);
				
				System.out.print("\n请输入注册的密码:  ");
				
				//用控制台运行可实现星号显示密码
				if ((System.console()) != null) {
					password = new Input().readPassword();
				} else {
					password = in.next();
				}
				
				System.out.print("\n请再次输入注册的密码:  ");

				//用控制台运行可实现星号显示密码
				if ((System.console()) != null) {
					password2 = new Input().readPassword();
				} else {
					password2 = in.next();
				}
				if (password2.equals(password)) {
					channel.send(password);
				} else {
					System.out.println("\n两次密码不相同！");
					login();
					return;
				}
					
				//提示信息
				System.out.println(channel.receive());
				break;
			case 2: 	
				System.out.print("\n请输入账号: ");
				account = in.next();
				channel.send(account);

				System.out.print("\n请输入密码:  ");
				
				//用控制台运行可实现星号显示密码
				if ((System.console()) != null) {
					password = new Input().readPassword();
				} else {
					password = in.next();
				}
				channel.send(password);
				
				//提示信息
				loginMsg = channel.receive();
				System.out.println(loginMsg);
				break;
			case 3:
				//获取用户昵称
				System.out.print("\n请输入你的昵称: ");
				name = new Send(client,null).send();
				
				//提示信息
				System.out.println(channel.receive());
				break;
			default:
				login();
				return;
		}
	}
}
