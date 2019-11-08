package com.test.Optional;

import java.util.Optional;

import com.test.Lambda.Test01.Employee;

public class TestOptional {
	public static void main(String[] args) {
		test01();
		test02();
		test03();
		test04();
		test05();
	}	

	private static void test01() {
		Optional<Employee> op = Optional.of(new Employee());
		System.out.println(op.get());
	}

	private static void test02() {
		Optional<Object> op = Optional.empty();
		System.out.println(op);
	}

	private static void test03() {
		Optional<Employee> op = Optional.ofNullable(null);
		
		if (op.isPresent()) {
			System.out.println(op.get());
		}
		
		Employee emp = op.orElse(new Employee("张三", 28, 1000));
		System.out.println(emp);
		
		Employee emp2 = op.orElseGet(() -> new Employee());
		System.out.println(emp2);
	}
	
	private static void test04() {
		Optional<Employee> op = Optional.ofNullable(new Employee("张三", 28, 1000));
		
		Optional<String> str = op.map(Employee::getName);
		System.out.println(str.get());
		
		//返回值为Optional，进一步避免空指针异常
		Optional<String> str2 = op.flatMap((e) -> Optional.of(e.getName()));
		System.out.println(str2.get());
	}
	
	private static void test05() {
		System.out.println(getGodnessName(null));
		System.out.println(getGodnessName2(null));
		
		Man man = new Man(new Godness("Jmc"));
		System.out.println(getGodnessName(man));
		
		NewMan man2 = new NewMan(Optional.ofNullable(new Godness("Athur")));
		System.out.println(getGodnessName2(man2));
	}
	
	private static String getGodnessName(Man man) {
		if (man != null) {
			Godness gn = man.getGodness();
			
			if (gn != null) {
				return gn.getName();
			}
		}
		
		return "比利海灵顿";
	}
	
	private static String getGodnessName2(NewMan man) {
		return Optional.ofNullable(man)
					   .orElse(new NewMan())
					   .getGodness()
					   .orElse(new Godness("香蕉君"))
					   .getName();
	}
}








