/**
 * 动态生成代理类代码
 * 也可用javassist等实现
 *
 * @author Jmc
 * 
 */

package com.test.gof23.structural.proxy.dynamic;

import java.lang.reflect.Proxy;

public class Client {
	public static void main(String[] args) {
		Star realStar = new RealStar();
		StarHandler handler = new StarHandler(realStar);
		
		Star proxy = (Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
												   //implements：Star
												   new Class[] {Star.class},
							                       handler);
		
		//除了sing其他都被过滤了（可以在StarHandler里面具体用if等实现
		//						  或者直接在StarHandler里面写）
		proxy.confer();
		proxy.signContract();
		proxy.bookTicket();
		proxy.sing();
		proxy.collectMoney();
	}
}
