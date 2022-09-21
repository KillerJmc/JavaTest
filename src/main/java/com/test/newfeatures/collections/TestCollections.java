package com.test.newfeatures.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestCollections {
	public static void main(String[] args) {
		test01();
		test02();
		test03();
		test04();
		test05();
	}

	//List, Set, LocalDate均支持of方法（用于创建对象）
	private static void test01() {
		var list = List.of(1, 2, 3, 4, 5);
		System.out.println(list);
		//不可添加，会报错（不可改变的集合）
//		list.add(6);
//		System.out.println(list);
		var list2 = Arrays.asList(6, 7, 8, 9);
		System.out.println(list2);
		//不可添加，会报错
//		list2.add(10);
//		System.out.println(list2);
		//两者原理不一样
		System.out.println("------------------------------------");
	}
	
	private static void test02() {
		//在添加重复元素时，不是无法添加，而是抛出异常
		//也不能再次添加
//		var set = Set.of(1, 2, 3, 3, 2);
		var set = Set.of(1, 2, 3);
		System.out.println(set.getClass());
		System.out.println("------------------------------------");
	}
	private static void test03() {
		//Stream也有of方法
		Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
		stream.forEach(System.out::println);
		
		System.out.println("------------------------------------");
		
		Stream<Object> stream2 = Stream.of();
		//流中没有数据
		stream2.forEach(System.out::println);
		
//		//传入的null会被解析成一个数组对象，会进一步访问他的长度信息
//		//报空指针异常
//		Stream<Object> stream3 = Stream.of(null);
//		stream3.forEach(System.out::println);
		
		//解决空指针问题
		Stream<Object> stream4 = Stream.ofNullable(null);
		stream4.forEach(System.out::println);
	}
	
	private static void test04() {
		Stream<Integer> stream1 = Stream.of(1, 3, 2, 4, 5);
		//take while, drop while
		//此处drop: 丢弃
		
		//捡起：一旦为假即终止
		stream1.takeWhile(t -> t % 2 != 0)
			   .forEach(System.out::println);
		
		System.out.println("------");
		
		stream1 = Stream.of(1, 3, 2, 4, 5);
		//丢弃：一旦为假即终止
		stream1.dropWhile(t -> t % 2 != 0)
			   .forEach(System.out::println);
		System.out.println("------------------------------------");
	}
	
	private static void test05() {
		//流的迭代，创建无限流
		Stream.iterate(1, t -> 2 * t + 1)
			  .limit(9)
			  .forEach(System.out::println);
		
		System.out.println("------");
		
		//有限迭代
		Stream.iterate(1, t -> t < 500 , t -> 2 * t + 1)
			  .forEach(System.out::println);
		
		System.out.println("------------------------------------");
		
		//质数流
		IntStream.range(2, 1000)
				 .filter(x ->
				    //(2, (int) Math.sqrt(x) + 1)也可以
				 	IntStream.range(2, x)
				 			 .filter(t -> x % t == 0)
				 			 .count() == 0
				 )
				 .forEach(System.out::println);
	}
}





