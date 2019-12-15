/**
 *  默认GC会不间断多次回收内存
 *  如果运行配置启动参数（参数-VM自变量）加上 -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *  把epsilonGC设为默认GC，则就不释放内存，直到内存不足报错，性能较高。
 *  
 *  而万众瞩目的ZGC（清理内存延迟不超过10ms）在Windows-jdk中不支持，但在Linux中支持。
 *  具体用法：-XX:+UnlockExperimentalVMOptions -XX:+UseZGC
 * 
 */

package com.test.gc;

import java.util.LinkedList;

public class GCTest {
	public static void main(String[] args) {
		var list = new LinkedList<Garbage>();
		int count = 0;
		
		while (true) {
			list.add(new Garbage());
			if (count++ == 500) list.clear();
		}
	}
}

class Garbage {
	int n = 1000;
	
	//这个方法GC在删除本对象时会调用一次
	@Override
	protected void finalize() throws Throwable {
		System.out.println("collecting " + this);
	}
}


