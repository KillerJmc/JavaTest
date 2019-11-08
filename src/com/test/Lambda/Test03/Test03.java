package com.test.Lambda.Test03;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jmc.array.Rand;
import com.test.Lambda.Test01.Employee;
import static com.test.Lambda.Test01.Test01.employees;

public class Test03 {
	private static List<Employee> emps = employees;
	
	public static void main(String[] args) {
		test();
		createPrimes(100);
	}

	private static void test() {
		//注意collect(Collectors中方法)
		//多级分组
		Map<Double, Map<Integer, List<Employee>>> map = emps.stream()
			.collect(
				Collectors.groupingBy(
					Employee::getSalary,
					Collectors.groupingBy(Employee::getAge)
				)
			);
		System.out.println(map);
		//分区
		Map<Boolean, List<Employee>> partition = emps.stream()
			.collect(
				Collectors.partitioningBy((e) -> e.getSalary() > 8000)
			);
		System.out.println(partition);
		
		//简易计算
		DoubleSummaryStatistics dss = emps.stream()
			.collect(Collectors.summarizingDouble(Employee::getSalary));
		//返回double
		dss.getMax();
		dss.getMin();
		dss.getSum();
		dss.getAverage();
		dss.getCount();
		
		//连接字符串(可设置分隔符joining(",")以及首尾符号joining(",","===","==="))
		String str = emps.stream()
					 .map(Employee::getName)
					 .collect(Collectors.joining());
		System.out.println(str);
		
//		Timer.timer(() -> {
//			//按顺序(sequential) cpu占用25% 66s
//			long result = LongStream.rangeClosed(0, 100000000000L)
//					  .reduce(0, Long::sum);
//			System.out.println("顺序执行" + result);
//		});
		
//		Timer.timer(() -> {
//			//并发: 多个任务在同一个 CPU 核上按细分的时间片轮流(交替)执行
//			//并行: 多个任务真正的分配到不同的 CPU 内核上去执行的，它们是真正的同时执行。
//			//并发是四辆汽车在同一个车道上跑; 并行是单向四车道，四辆车在各自的车道跑，彼此不受影响。
//			
//			//并行(parallel) cpu占用100% 22s
//			//底层是fork-join模式
//			long result = LongStream.rangeClosed(0, 100000000000L)
//					  .parallel()
//					  .reduce(0, Long::sum);
//			System.out.println("并行执行" + result);
//		});

	}

	/**
	 * 产生质数
	 */
	public static void createPrimes(int range) {
		IntStream.rangeClosed(2, range)
				 //range不包括后面节点 rangeClosed包括
				 .filter(i -> IntStream.range(2, i)
				 					   .allMatch(k -> i % k != 0))
				 .forEach(System.out::println);
	}
}
