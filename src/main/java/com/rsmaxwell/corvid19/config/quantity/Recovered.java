package com.rsmaxwell.corvid19.config.quantity;

public class Recovered extends Quantity {

	public Recovered(double value) {
		super(value);
	}

	@Override
	public double rate(double t, double S, double I, double R) {
		return recovery * I;
	}

}
