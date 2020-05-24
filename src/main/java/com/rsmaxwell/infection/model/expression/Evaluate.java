package com.rsmaxwell.infection.model.expression;

import com.rsmaxwell.infection.model.app.MySecurityManager;
import com.rsmaxwell.infection.model.app.SecurityMode;

import groovy.util.Eval;

public class Evaluate implements Expression {

	private String string;

	public Evaluate(String string) {
		this.string = string;
	}

	@Override
	public double getValue(double t, String id1, String id2) throws Exception {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return evaluate(t, id1, id2);
		}

		if (!(securityManager instanceof MySecurityManager)) {
			return evaluate(t, id1, id2);
		}

		MySecurityManager mySecurityManager = (MySecurityManager) securityManager;
		try {
			mySecurityManager.setSecurityMode(SecurityMode.LOCKDOWN);
			return evaluate(t, id1, id2);
		} finally {
			mySecurityManager.setSecurityMode(SecurityMode.OPEN);
		}
	}

	private double evaluate(double t, String id1, String id2) throws Exception {

		Object object = Eval.xyz("t", "id1", "id2", string);

		if (object == null) {
			throw new Exception("Expression '" + string + "' returned null");
		}

		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}

		throw new Exception("Expression '" + string + "' returned unexpected type: " + object.getClass().getSimpleName());
	}
}
