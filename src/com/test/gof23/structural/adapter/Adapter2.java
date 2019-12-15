/**
 * 适配器（对象适配器：组合的方式）
 * @author Jmc
 * 
 */
package com.test.gof23.structural.adapter;


public class Adapter2 implements Target {
	private Adaptee adaptee;
	
	public Adapter2(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void handleReq() {
		adaptee.request();
	}
}
