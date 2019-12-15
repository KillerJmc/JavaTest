/**
 * 命令模式，不常用，了解即可
 * @author Jmc
 * 
 */
package com.test.gof23.behavior.command;

public class Client {
	public static void main(String[] args) {
		Command c = new ConcreteCommand(new Receiver());
		Invoker i = new Invoker(c);
		//调用
		i.call();
		
		/**
		 * 效果一样，不过可以让程序有更强的扩展性
		 */
		new Receiver().action();
	}
}
