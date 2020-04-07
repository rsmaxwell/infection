package com.rsmaxwell.infection.quantity;

public abstract class Quantity implements RateFunction {

	public double value;

	public Quantity(double value) {
		this.value = value;
	}

	public void add(double value) {
		this.value += value;
	}
}
