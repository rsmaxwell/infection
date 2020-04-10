package com.rsmaxwell.infection.quantity;

public abstract class Quantity implements RateFunction {

	public double value;
	public double delta;

	public Quantity(double value) {
		this.value = value;
	}
}
