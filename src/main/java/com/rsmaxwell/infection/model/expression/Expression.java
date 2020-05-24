package com.rsmaxwell.infection.model.expression;

public interface Expression {

	double getValue(double t, String id1, String id2) throws Exception;
}
