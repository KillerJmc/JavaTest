package com.test.GOF23.BehaviorPattern.Iterator;

public class Client {
	public static void main(String[] args) {
		var cma = new ConcreteMyAggregate();
		cma.addObject("aa", "bb", "cc");
		
		MyIterator it = cma.createIterator();
		while (it.hasNext()) {
			System.out.println(it.getCurrentObj());
			it.next();
		}
	}
}
