package com.test.newfeatures.optional;

import java.util.Optional;

import com.test.newfeatures.lambda.test01.Employee;

public class TestOptional {
	public static void main(String[] args) {
		test01();
		test02();
		test03();
		test04();
		test05();
		test06();
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
		
		Employee emp2 = op.orElseGet(Employee::new);
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
		System.out.println(getGoddessName(null));
		System.out.println(getGoddessName2(null));
		
		Man man = new Man(new Goddess("Jmc"));
		System.out.println(getGoddessName(man));
		
		NewMan man2 = new NewMan(Optional.ofNullable(new Goddess("Athur")));
		System.out.println(getGoddessName2(man2));
	}
	
	private static String getGoddessName(Man man) {
		if (man != null) {
			Goddess gn = man.getGoddess();
			
			if (gn != null) {
				return gn.getName();
			}
		}
		
		return "比利海灵顿";
	}
	
	private static String getGoddessName2(NewMan man) {
		return Optional.ofNullable(man)
					   .orElse(new NewMan())
					   .getGoddess()
					   .orElse(new Goddess("香蕉君"))
					   .getName();
	}

	private static void test06() {
		Optional<Employee> op = Optional.ofNullable(null);
		//op.orElseGet(Supplier s) 这个可以用supplier 更灵活

		//抛出异常或返回值
		System.out.println(op.orElseThrow());
	}
}








