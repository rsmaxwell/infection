package com.rsmaxwell.infection.model.expression;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilerConfiguration;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class MyScript extends Expression {

	private String script;

	public MyScript(String script) {
		this.script = script + ".groovy";
	}

	@Override
	public double getValue(double time, String group1, String group2) throws Exception {

		GroovyScriptEngine engine = getGroovyScriptEngine();
		Binding binding = getBinding(time, group1, group2);
		Object object = engine.run(script, binding);

		if (object == null) {
			throw new Exception("Script '" + script + "' returned null : " + bindingToString(time, group1, group2));
		}

		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}

		throw new Exception("Script '" + script + "' returned unexpected type: " + object.getClass().getSimpleName() + " : " + bindingToString(time, group1, group2));
	}

	// Create a groovyScriptEngine which only supports arithmetic operations
	private GroovyScriptEngine getGroovyScriptEngine() throws IOException {

		CompilerConfiguration config = getCompilerConfiguration();

		File scriptsDir = new File("./scripts");
		String[] scripts = new String[] { scriptsDir.getAbsolutePath() };

		GroovyScriptEngine engine = new GroovyScriptEngine(scripts);
		engine.setConfig(config);
		return engine;
	}
}
