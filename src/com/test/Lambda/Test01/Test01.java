package com.test.Lambda.Test01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Test01 {

	public static void main(String[] args) {
		test07();
	}

	private static void test01() {
		Comparator<Integer> com = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		};
		
		TreeSet<Integer> list = new TreeSet<Integer>(com);
	}
	
	private static void test02() {
		Comparator<Integer> com = (o1, o2) -> o1 - o2;
		
		TreeSet<Integer> list = new TreeSet<Integer>(com);
	}
	
	public static List<Employee> employees = Arrays.asList(
			new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 5555.55),
			new Employee("王五", 50, 6666.66),
			new Employee("赵六", 16, 3333.33),
			new Employee("田七", 8, 7777.77)
	);	
	
	//获取当前公司年龄大于35的员工
	private static List<Employee> ageFilter(List<Employee> list) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (emp.getAge() > 35) {
				emps.add(emp);
			}
		}
		return emps;
	}
	
	private static void test03() {
		List<Employee> list = ageFilter(employees);
		for (Employee emp : list) {
			System.out.println(emp);
		}
	}
	
	//获取当前公司员工工资大于5000的员工信息
	private static List<Employee> salaryFilter(List<Employee> list) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (emp.getSalary() > 5000) {
				emps.add(emp);
			}
		}
		return emps;
	}
	
	
	
	public static List<Employee> employeeFilter(List<Employee> list, MyPredicate<Employee> mp) {
		List<Employee> emps = new ArrayList<Employee>();
		
		for (Employee emp : list) {
			if (mp.test(emp)) {
				emps.add(emp);
			}
		}
		return emps;
	}
	
	
	//优化方式一: 策略设计模式
	private static void test04() {
		List<Employee> list = employeeFilter(employees,new FilterByAge());
		
		for (Employee emp : list) {
			System.out.println(emp);
		}
		
		System.out.println("------------------------------------------------------");
		
		List<Employee> list2 = employeeFilter(employees,new FilterBySalary());
		for (Employee emp : list2) {
			System.out.println(emp);
		}
	}

	//优化方式二: 匿名内部类
	private static void test05() {
		List<Employee> list = employeeFilter(employees, new MyPredicate<Employee>() {
			@Override
			public boolean test(Employee t) {
				return t.getSalary() < 5000;
			}
		});
		for (Employee emp : list) {
			System.out.println(emp);
		}
	}
	
	//优化方式三: Lambda表达式
	private static void test06() {
		List<Employee> list = employeeFilter(employees, (e) -> e.getSalary() < 5000);
		list.forEach(System.out::println);
	}
	
	//优化方式四: Stream API
	private static void test07() {
		employees.stream()
				 .filter((e) -> e.getSalary() > 5000)
				 .limit(2)
				 .forEach(System.out::println);
		
		System.out.println("------------------------------------------------------------");
		employees.stream()
				 .map(Employee::getName)
				 .forEach(System.out::println);
	}
}








