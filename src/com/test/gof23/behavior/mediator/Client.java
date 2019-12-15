/**
 * 中介者模式
 * @author Jmc
 * 
 */
package com.test.gof23.behavior.mediator;

public class Client {
	public static void main(String[] args) {
		Mediator m = new President();
		
		Market market = new Market(m);
		Development dev = new Development(m);
		Financial f = new Financial(m);
		
		market.selfAction();
		market.outAction();
	}
}
