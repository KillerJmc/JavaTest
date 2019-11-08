package com.test.GOF23.BehaviorPattern.Strategy;

public interface Strategy {
	public double getPrice(double originalPrice);
}

class NewCustomerFewStrategy implements Strategy {
	@Override
	public double getPrice(double originalPrice) {
		System.out.println("不打折，原价");
		return originalPrice;
	}
}

class NewCustomerManyStrategy implements Strategy {
	@Override
	public double getPrice(double originalPrice) {
		System.out.println("打九折");
		return originalPrice * 0.9;
	}
}

class OldCustomerFewStrategy implements Strategy {
	@Override
	public double getPrice(double originalPrice) {
		System.out.println("打八五折");
		return originalPrice * 0.85;
	}
}

class OldCustomerManyStrategy implements Strategy {
	@Override
	public double getPrice(double originalPrice) {
		System.out.println("打八折");
		return originalPrice * 0.8;
	}
}
