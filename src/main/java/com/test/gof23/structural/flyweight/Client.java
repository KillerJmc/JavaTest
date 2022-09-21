/**
 * 实现外部状态和内部状态的分离，减少新对象数量
 * 从而减少内存消耗！
 * @author Jmc
 */
package com.test.gof23.structural.flyweight;

public class Client {
	public static void main(String[] args) {
		ChessFlyWeight chess1 = ChessFlyWeightFactory.getChess("黑色");
		ChessFlyWeight chess2 = ChessFlyWeightFactory.getChess("黑色");
		System.out.println(chess1);
		System.out.println(chess2);
		
		System.out.println("添加外部状态的处理--------------------------");
		//缺点：每次传入新对象，会增加运行时间
		chess1.display(new Coordinate(10, 10));
		chess2.display(new Coordinate(20, 20));
	}
}
