package com.test.NewFeatures.NewOptional;

import java.util.Optional;

import com.test.Lambda.Test01.Employee;

public class OptionalTest {
	public static void main(String[] args) {
		test01();
	}

	private static void test01() {
		Optional<Employee> op = Optional.ofNullable(null);
		//op.orElseGet(Supplier s) 这个可以用supplier 更灵活
		
		//抛出异常或返回值
		System.out.println(op.orElseThrow());
	}
}
