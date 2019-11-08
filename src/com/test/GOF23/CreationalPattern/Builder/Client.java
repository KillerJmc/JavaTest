package com.test.GOF23.CreationalPattern.Builder;

/**
 * 建造者模式
 * @author Jmc
 *
 */

public class Client {
	public static void main(String[] args) {
		AirShipDirector director = new XAirShipDirector(new XAirShipBuilder());
		AirShip ship = director.derectAirShip();
		
		System.out.println(ship.toString());
		ship.launch();
	}
}
