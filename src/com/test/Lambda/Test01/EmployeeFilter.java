package com.test.Lambda.Test01;

class FilterByAge implements MyPredicate<Employee>{

	@Override
	public boolean test(Employee t) {
		return t.getAge() > 35;
	}
	
}

class FilterBySalary implements MyPredicate<Employee>{

	@Override
	public boolean test(Employee t) {
		return t.getSalary() > 5000;
	}
	
}
