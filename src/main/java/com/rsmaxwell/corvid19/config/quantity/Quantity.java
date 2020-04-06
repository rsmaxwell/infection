package com.rsmaxwell.corvid19.config.quantity;

public abstract class Quantity implements RateFunction {

	static double transmission = 3.2;
	static double recovery = 0.23;

	public double value;

	public Quantity(double value) {
		this.value = value;
	}

	public void add(double value) {
		this.value += value;
	}
}
