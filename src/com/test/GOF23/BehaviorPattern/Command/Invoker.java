package com.test.GOF23.BehaviorPattern.Command;

/**
 * 调用者，发起者
 * @author Jmc
 *
 */
public class Invoker {
	/**
	 * 可用集合包含多条命令
	 */
	private Command command;

	public Invoker(Command command) {
		this.command = command;
	}
	
	/**
	 * 业务方法，用于调用命令类的方法
	 */
	public void call() {
		command.execute();
	}
}
