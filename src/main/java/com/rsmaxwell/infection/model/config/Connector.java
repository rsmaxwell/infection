package com.rsmaxwell.infection.model.config;

import com.rsmaxwell.infection.model.expression.Expression;

public class Connector {

	private Expression transmission;

	public double getTransmission(double t, String id1, String id2) throws Exception {
		return transmission.getValue(t, id1, id2);
	}

	public void setTransmission(Expression expression) {
		this.transmission = expression;
	}
}
