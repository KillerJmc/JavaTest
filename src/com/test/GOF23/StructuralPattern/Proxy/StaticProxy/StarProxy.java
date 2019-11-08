/**
 * 
 * @author Jmc
 * 
 */
package com.test.GOF23.StructuralPattern.Proxy.StaticProxy;

public class StarProxy implements Star {
	private Star star;
	
	public StarProxy(Star star) {
		this.star = star;
	}

	@Override
	public void confer() {
		System.out.println("ProxyStar.confer()");
	}

	@Override
	public void signContract() {
		System.out.println("ProxyStar.signContract()");
	}

	@Override
	public void bookTicket() {
		System.out.println("ProxyStar.bookTicket()");
	}

	@Override
	public void sing() {
		star.sing();
	}

	@Override
	public void collectMoney() {
		System.out.println("ProxyStar.collectMoney()");
	}
}
