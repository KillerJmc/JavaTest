/**
 * 适配器（类适配方法）
 * @author Jmc
 * 
 */

package com.test.gof23.structural.adapter;

public class Adapter extends Adaptee implements Target {
	@Override
	public void handleReq() {
		super.request();
	}
}
