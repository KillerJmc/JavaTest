/**
 * 享元工厂类
 * @author Jmc
 */
package com.test.gof23.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ChessFlyWeightFactory {
	//享元池
	private static Map<String, ChessFlyWeight> map = new HashMap<>();
	
	public static ChessFlyWeight getChess(String color) {
		if (map.containsKey(color)) {
			return map.get(color);
		} else {
			var cfw = new ConcreteChess(color);
			map.put(color, cfw);
			return cfw;
		}
	}
}






