/**
 * 静态代理
 * @author Jmc
 * 
 */

package com.test.GOF23.StructuralPattern.Proxy.StaticProxy;

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
