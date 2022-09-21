package com.test.gof23.structural.composite;

/**
 * 抽象组件
 */
public interface Component {
	void operation();
}

/**
 * 叶子组件（无子节点）
 */
interface Leaf extends Component {
	
}

/**
 * 复合（容器组件）
 */
interface Composite extends Component {
	void add(Component c);
	void remove(Component c);
	Component getChild(int index);
}


