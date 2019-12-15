package com.test.gof23.behavior.mediator;

public class Financial implements Department {
	/**
	 * 持有中介者（总经理）的引用
	 */
	private Mediator m;
	
	public Financial(Mediator m) {
		this.m = m;
		m.register("financial", this);
	}

	@Override
	public void selfAction() {
		System.out.println("数钱	！");
	}

	@Override
	public void outAction() {
		System.out.println("汇报工作！钱太多了，怎么花？");
	}
}
