package com.rsmaxwell.infection.model.expression;

import java.io.IOException;

import org.codehaus.groovy.control.CompilerConfiguration;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class Evaluate extends Expression {

	private String string;

	public Evaluate(String string) {
		this.string = string;
	}

	@Override
	public double getValue(double time, String group1, String group2) throws Exception {

		Binding binding = getBinding(time, group1, group2);
		GroovyShell shell = getGroovyShell(binding);
		Object object = shell.evaluate(string);

		if (object == null) {
			throw new Exception("Expression '" + string + "' returned null : " + bindingToString(time, group1, group2));
		}

		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}

		throw new Exception("Expression '" + string + "' returned unexpected type: " + object.getClass().getSimpleName() + " : " + bindingToString(time, group1, group2));
	}

	// Create a GroovyShell which only supports arithmetic operations
	private GroovyShell getGroovyShell(Binding binding) throws IOException {
		CompilerConfiguration config = getCompilerConfiguration();
		GroovyShell shell = new GroovyShell(binding, config);
		return shell;
	}

}
