package com.test.GOF23.BehaviorPattern.Iterator;

public interface MyIterator {
	/**
	 * 将游标指向第一个，下一个元素
	 */
	void first();
	void next();
	boolean hasNext();
	
	boolean isFirst();
	boolean isLast();
	
	/**
	 * 获取当前游标指向的对象
	 */
	Object getCurrentObj();
}
