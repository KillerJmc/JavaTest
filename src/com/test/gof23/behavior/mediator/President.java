package com.test.gof23.behavior.mediator;

import java.util.HashMap;
import java.util.Map;

public class President implements Mediator {
	private Map<String, Department> map = new HashMap<>();
	
	@Override
	public void register(String dName, Department d) {
		map.put(dName, d);
	}

	@Override
	public void command(String dName) {
		map.get(dName).selfAction();
	}
}
