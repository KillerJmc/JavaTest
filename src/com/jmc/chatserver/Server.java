/**
 * 作者: Jmc
 * 时间: 2019.3.2
 * 功能: Socket服务器
 */

package com.jmc.chatserver;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.text.*;
import com.jmc.exception.*;
import com.jmc.io.*;

public class Server
{
	//服务器
	private ServerSocket server;
	//客户端
	private Socket socket;
	//放置客户端的集合
	public static List<Channel> list;
	//数据库路径
	private String dataPath;
	//数据库文件夹
	private File dataBase;
	//用户文件夹路径
	private String userPath;
	//用户文件夹
	private File userData;
	//用户状态
	private int state;
	//用户昵称
	private String name;
	//用户账号
	private String account;
	//用户密码
	private String password;
	//获取用户信息的管道
	private Channel channel;
	//运行标识
	private boolean isRunning = true;
	
	public static void main(String[] args) throws Exception
	{	
		//初始化
		new Server().init();
	}
	
	//初始化
	public void init() throws Exception {
		//数据库路径
		dataPath = "/sdcard/Server/";
		dataBase = new File(dataPath);
		//判断数据库是否存在
		if (!dataBase.exists()) {
			//若不存在即创建
			dataBase.mkdirs();
			System.out.println("\n数据库创建成功！");
		} else {
			System.out.println("\n数据库启动成功！");
		}
		//创建服务器
		create();
	}
	
	//创建服务器
	public void create() throws Exception {
		try {
			//创建服务器
			server = new ServerSocket(8888);
		} catch(Exception e) {
			System.out.println("\n" + ExceptionHandler.showCaused(e));
			return;
		}
		
		//定义用户数组
		list = new CopyOnWriteArrayList<Channel>();
		
		//显示信息
		System.out.println("\n服务器已成功启动！\n\n");
		//输出到数据库
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Files.out(("----------------------------\n"
			+ sdf.format(new Date())
			+ "\n\n服务器已成功启动!\n\n\n"),(dataPath + "DataBase/Record"),true);
	
		
		
		//线程控制服务端
		new Thread(new Runnable(){
			public void run() {
				while (isRunning) {
					//获取输入
					Scanner input = new Scanner(System.in);
					//回车做为换行符
					input.useDelimiter("\n");
					//获取输入的命令
					String command = input.next();				
					try {
						//输出指令到数据库
						Files.out(("服务器: " + command + "\n\n"),(dataPath + "DataBase/Record"),true);
					} catch(Exception e) {
						System.out.println(ExceptionHandler.showCaused(e));
					}
					
					//指令
					if (command.contains("kick")) {
						//格式: kick user
						String userName = command.replace("kick ","");
						for (Channel user : list) {
							if (user.name.equals(userName)){
								user.beKicked = true;
								user.exit();
								break;
							}
						}
					} else if (command.contains("@") & command.contains(":")) {
						//发送内容
						String content = null;
						//读取内容
						content = command.substring(command.indexOf(":") + 2);
						
						//格式: @all: content
						if (command.contains("all")) {
							Channel.sendToAll("服务器: " + content);
						} else {
							//格式: @user: content
							String userName = command.substring(1,command.indexOf(":"));
							Channel.sendToSomeone(userName,"服务器仅向你说: " + content);
						}
						
						//输出空行
						System.out.println();
					} else if(command.equals("users")) {
						String users = "";
						//遍历集合
						for (Channel user : list) {
							users += user.name + ", ";			
						}
						users = "\n\n" + users.substring(0, users.length() - 2) + "\n\n";
						//系统输出
						System.out.println(users);
						//输出到数据库
						try {
							Files.out(users.substring(1) + "\n",(dataPath + "DataBase/Record"),true);
						} catch(Exception e) {
							System.out.println(ExceptionHandler.showCaused((e)));
						}
					} else if(command.equals("help")) {
						System.out.println("\n" + "@all: content    发送给所有人"
										  + "\n" + "@user: content    私发给某用户"
										  + "\n" + "kick user    踢出某用户"
										  + "\n" + "users    列出所有用户"
										  + "\n" + "records    列出聊天记录"
										  + "\n" + "stop    关闭服务器" + "\n"
						);
					} else if(command.equals("records")) {
						//输出聊天记录
						try {
							System.out.println("\n-------------------------------------\n"
								+ Files.read(dataPath + "DataBase/Record")
							    + "-------------------------------------\n");
						} catch (Exception e) {}
					} else if(command.equals("stop")) {
						//停止运行
						isRunning = false;
						try {
							server.close();
							for (Channel user : list) {
								user.exit();
							}
						} catch(Exception e) {
							System.out.println(ExceptionHandler.showCaused(e));
						}
						//显示信息
						System.out.println("\n服务器已停止运行" + "\n");
						//输出到数据库
						try {
							Files.out(("服务器已停止运行" + "\n\n"),(dataPath + "DataBase/Record"),true);
						} catch(Exception e) {
							System.out.println(ExceptionHandler.showCaused((e)));
						}
						//返回
						return;
					}
				}
			}
		}).start();
		
		while (isRunning) {
			//接受客户端
			try {
				socket = server.accept();	
			} catch(Exception e) {
				continue;
			}
			
			//多线程(防止堵塞)
			new Thread(new Runnable(){
				public void run() {
					//管道初始化
					channel = new Channel(socket,null);

					//获取用户信息
					state = Integer.parseInt(channel.receive());
					
					try {
						//判断
						loop : switch (state) {
							case 1:
								//获取用户信息
								name = channel.receive();
								account = channel.receive();
								password = channel.receive();

								//建立用户文件夹
								userPath = dataPath + "Users/" + name;
								userData = new File(userPath);
								if (!userData.exists()) userData.mkdirs();

								//放入账号密码
								Files.out(account,(userPath + "/Account"),false);
								Files.out(password,(userPath + "/Password"),false);

								//发送提示信息
								channel.send("\n\n注册成功，已自动登录\n\n");
								break;
							case 2:
								//获取用户信息
								account = channel.receive();
								password = channel.receive();

								//判断是否账号密码正确
								File[] users = new File(dataBase.getAbsolutePath() + "/Users").listFiles();
								for (File user : users) {
									File[] data = user.listFiles();
									for (File f : data) {
										if (f.getName().equals("Account")) {
											if (Files.read(f).equals(account)) {
												String passwordPath = f.getParent() + "/Password";
												if (Files.read(passwordPath).equals(password)) {
													name = f.getParentFile().getName();
													//发送提示信息
													channel.send("\n\n欢迎回来，" + name + "\n\n");
													break loop;
												}
											}
										}
									}
								}
								//发送提示信息
								channel.send("\n账号或密码错误");
								return;
							case 3:
								name = channel.receive();
								//发送提示信息
								channel.send("\n欢迎加入聊天室\n\n");
								break;							
						}
						
						//定义客户端同步管道
						Channel client = new Channel(socket,name);

						//放入集合
						list.add(client);		

						//显示信息
						System.out.println(name + "已加入服务器\n");
						
						//输出到数据库
						Files.out((name + "已加入服务器\n\n"),(dataPath + "DataBase/Record"),true);

						//告知其他人新用户加入的信息
						new Channel(socket,name).sendToOthers(name + "加入了聊天室");

						//开启同步
						new Thread(client).start();
					} catch (Exception e) {
						System.out.println(ExceptionHandler.showCaused(e));
						//输出到数据库
						try {
							Files.out((ExceptionHandler.showCaused(e) + "\n\n"), (dataPath + "DataBase/Record"), true);
						} catch (Exception p){}
					}
				}
			}).start();
		}
	}
}
