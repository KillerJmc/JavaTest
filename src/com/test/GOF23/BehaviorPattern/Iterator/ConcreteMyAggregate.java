/**
 * 自定义聚合类（Aggregate['æɡrɪɡət] ）
 * @author Jmc
 */

package com.test.GOF23.BehaviorPattern.Iterator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteMyAggregate {
	private List<Object> list = new ArrayList<>();

	public ConcreteMyAggregate() {}
	
	/**
	 	public void addObject(Object obj) {
	 		this.list.add(obj);
	 	}
	 */
	public void addObject(Object... obj) {
		for (Object o : obj) {
			this.list.add(o);
		}
	}
	
	
	public void removeObject(Object obj) {
		this.list.remove(obj);
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
	
	/**
	 * 获取迭代器
	 */
	public MyIterator createIterator() {
		return new ConcreteIterator();
	}
	
	/**
	 * 使用内部类定义迭代器，可以直接使用外部类属性
	 * @author Jmc
	 */
	private class ConcreteIterator implements MyIterator {
		/**
		 * 定义游标用于记录遍历时的位置
		 */
		private int cursor;
		
		@Override
		public void first() {
			this.cursor = 0;
		}

		@Override
		public void next() {
			if (cursor < list.size()) cursor++;
		}

		@Override
		public boolean hasNext() {
			return cursor < list.size();
		}

		@Override
		public boolean isFirst() {
			return cursor == 0;
		}

		@Override
		public boolean isLast() {
			return cursor == list.size() - 1;
		}

		@Override
		public Object getCurrentObj() {
			return list.get(cursor);
		}
	}
}
