package com.test.GOF23.BehaviorPattern.Mediator;

/**
 * 中介者
 * @author Jmc
 *
 */
public interface Mediator {
	void register(String dName, Department d);
	
	void command(String dName); 
}
