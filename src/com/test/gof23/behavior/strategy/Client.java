/**
 * 策略模式（ Strategy [ˈstrætədʒi]）
 * @author Jmc
 */
package com.test.gof23.behavior.strategy;

public class Client {
	public static void main(String[] args) {
		Strategy s1 = new OldCustomerManyStrategy();
		Context ctx = new Context(s1);
		ctx.printPrice(1299);
	}
}
