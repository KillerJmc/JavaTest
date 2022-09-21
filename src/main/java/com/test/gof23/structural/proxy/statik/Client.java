/**
 * 静态代理
 * @author Jmc
 * 
 */

package com.test.gof23.structural.proxy.statik;

public class Client {
	public static void main(String[] args) {
		Star star = new RealStar();
		Star proxy = new StarProxy(star);
		
		proxy.confer();
		proxy.signContract();
		proxy.bookTicket();
		proxy.sing();
		proxy.collectMoney();
	}
}
