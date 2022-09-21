/**
 * 桥接模式
 * 用来解决多个维度的问题（多继承）
 * 和装饰者模式区别：没有继承自同一个接口
 * 具体分析如下：
 * 1.桥接模式中所说的分离，其实是指将结构与实现分离（当结构和实现有可能发生变化时）或属性与基于属性的行为进行分离；而装饰者只是对基于属性的行为进行封闭成独立的类。
 * 2.桥接中的行为是横向的行为，行为彼此之间无关联；而装饰者模式中的行为具有可叠加性，其表现出来的结果是一个整体，一个各个行为组合后的一个结果。
 *
 * @author Jmc
 * 
 */

package com.test.gof23.structural.bridge;

public class Client {
	public static void main(String[] args) {
		Computer2 c = new Laptop2(new Lenovo());
		c.sale();
		
		Computer2 c2 = new Pad2(new Dell());
		c2.sale();
	}
}
