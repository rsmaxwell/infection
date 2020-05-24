package com.rsmaxwell.infection.model.expression;

public class Value implements Expression {

	private double value;

	public Value(double value) {
		this.value = value;
	}

	@Override
	public double getValue(double t, String id1, String id2) {
		return value;
	}
}
