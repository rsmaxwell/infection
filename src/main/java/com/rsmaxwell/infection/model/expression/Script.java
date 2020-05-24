package com.rsmaxwell.infection.model.expression;

import java.io.File;

import com.rsmaxwell.infection.model.app.MySecurityManager;
import com.rsmaxwell.infection.model.app.SecurityMode;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class Script implements Expression {

	private String script;

	public Script(String script) {
		this.script = script + ".groovy";
	}

	@Override
	public double getValue(double t, String id1, String id2) throws Exception {

		File scriptsDir = new File("./scripts");
		String[] scripts = new String[] { scriptsDir.getAbsolutePath() };
		GroovyScriptEngine engine = new GroovyScriptEngine(scripts);

		Binding binding = new Binding();
		binding.setVariable("time", t);
		binding.setVariable("group1", id1);
		binding.setVariable("group2", id2);

		SecurityManager securityManager = System.getSecurityManager();

		Object object = null;
		if (securityManager == null) {
			object = engine.run(script, binding);

		} else if (!(securityManager instanceof MySecurityManager)) {
			object = engine.run(script, binding);

		} else {
			MySecurityManager mySecurityManager = (MySecurityManager) securityManager;
			try {
				mySecurityManager.setSecurityMode(SecurityMode.LOCKDOWN);
				object = engine.run(script, binding);
			} finally {
				mySecurityManager.setSecurityMode(SecurityMode.OPEN);
			}
		}

		if (object == null) {
			throw new Exception("Script '" + script + "' returned null. { time: " + t + ", id1: " + id1 + ", id2: " + id2 + " }");
		}

		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}

		throw new Exception("Script '" + script + "' returned unexpected type: " + object.getClass().getSimpleName() + "{ time: " + t + ", id1: " + id1 + ", id2: " + id2 + " }");
	}
}
