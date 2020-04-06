package com.rsmaxwell.infection.quantity;

public class Infected extends Quantity {

	public Infected(double value) {
		super(value);
	}

	@Override
	public double rate(double t, double S, double I, double R) {
		return transmission * S * I - recovery * I;
	}

}
