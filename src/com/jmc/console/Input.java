/**
 *  作者: Jmc
 *  参考: sheting(原作者)
 *  时间: 2018.5.1
 *	功能: 控制台的输入控制
 *
 **/

package com.jmc.console;
 
import java.util.*;

public class Input
{
	//获取用户输入的密码
	public String readPassword(String character, long delayTime) {
		//判断是否为IDE(非控制台)
		if ((System.console()) == null) {
			System.out.println("您可能用了IDE启动了该程序，请用控制台重试");
			System.exit(0);
		}
		
		//新建线程对象
		EraserThread eraThread = new EraserThread(character,delayTime);
		//开启线程
		eraThread.start();
		
		//获取用户输入
		Scanner in = new Scanner(System.in);
		String s = in.next();
		
		//取消活跃状态，结束线程
		eraThread.isActive = false;
		
		return s;
	}
	
	public String readPassword() {
		return readPassword("*",10);
	}
	
	//擦除的线程
	private class EraserThread extends Thread {
		//是否处于活跃状态
		private boolean isActive;
		//替换的字符
		private String character;
		//延迟时间
		private long delayTime;

		//构造函数
		private EraserThread(String character, long delayTime) {
			//退格键加要替换的字符
			this.character = "\010" + character;
			this.delayTime = delayTime;
			this.isActive = true;
		}

		@Override
		public void run() {
			//活跃状态时不断替换
			while (isActive) {
				System.out.print(character);
				//休眠Xms
				try {
					Thread.sleep(delayTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//删除最后一个星号
			System.out.print("\010");
		}
	}
}
 
