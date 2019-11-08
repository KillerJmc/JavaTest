package com.test.GOF23.BehaviorPattern.Command;

public interface Command {
	/**
	 * 实际项目可以设计多个不同的方法（可带参数）
	 */
	void execute();
}

class ConcreteCommand implements Command {
	/**
	 * 命令的真正执行者
	 */
	private Receiver receiver;
	
	public ConcreteCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * 可在调用前后进行相关处理！（记录日志等）
	 */
	@Override
	public void execute() {
		receiver.action();
	}
}
