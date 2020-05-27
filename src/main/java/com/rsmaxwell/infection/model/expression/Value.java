package com.rsmaxwell.infection.model.expression;

public class Value extends Expression {

	private double value;

	public Value(double value) {
		this.value = value;
	}

	@Override
	public double getValue(double time, String group1, String group22) {
		return value;
	}
}
