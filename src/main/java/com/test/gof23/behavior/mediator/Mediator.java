package com.test.gof23.behavior.mediator;

/**
 * 中介者
 * @author Jmc
 *
 */
public interface Mediator {
	void register(String dName, Department d);
	
	void command(String dName); 
}
