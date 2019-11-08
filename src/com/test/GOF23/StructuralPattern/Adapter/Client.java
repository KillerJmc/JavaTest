/**
 * 客户端类（笔记本：只有USB接口）
 * @author Jmc
 * 
 */

package com.test.GOF23.StructuralPattern.Adapter;

public class Client {
	private void exec(Target t) {
		t.handleReq();
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		
		//1.
		Target t = new Adapter();
		c.exec(t);
		
		//2.
		Adaptee a = new Adaptee();
		t = new Adapter2(a);
		c.exec(t);
	}
}





