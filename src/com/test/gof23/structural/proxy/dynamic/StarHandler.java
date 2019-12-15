package com.test.gof23.structural.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StarHandler implements InvocationHandler {
	Star realStar;
	
	public StarHandler(Star realStar) {
		this.realStar = realStar;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object o = null;
		
		if (method.getName().equals("sing")) {
			o = method.invoke(realStar, args);
		}
		
		return o;
	}
}

/**
 * 代码生成的真实代理类结构
 	public class StarProxy implements Star {
 		StarHandler handler;
 		
 		public StarProxy(StarHandler handler) {
 			this.handler = handler;
 		}
 		
 		public void bookTicket() {
 			handler.invoke(this, 当前方法，args);
 		}
 		
 		...（以下其他方法里面均为invoke方法，全部汇总到StarHandler）
 	}
 */
