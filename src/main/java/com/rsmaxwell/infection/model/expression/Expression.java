package com.rsmaxwell.infection.model.expression;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.codehaus.groovy.syntax.Types;

import groovy.lang.Binding;

public abstract class Expression {

	public abstract double getValue(double t, String id1, String id2) throws Exception;

	public Binding getBinding(double t, String group1, String group2) {
		Binding binding = new Binding();
		binding.setVariable("time", t);
		binding.setVariable("group1", group1);
		binding.setVariable("group2", group2);
		return binding;
	}

	public CompilerConfiguration getCompilerConfiguration() throws IOException {

		ImportCustomizer importCustomizer = new ImportCustomizer();
		importCustomizer.addStaticStars("java.lang.Math");

		SecureASTCustomizer secure = new SecureASTCustomizer();
		secure.setClosuresAllowed(true);
		secure.setMethodDefinitionAllowed(true);

		//@formatter:off
		secure.setImportsWhitelist(Arrays.asList(
			"java.lang.Math"
			));
		
		secure.setStaticImportsWhitelist(Arrays.asList(
			));

        secure.setStaticStarImportsWhitelist(Arrays.asList(
            "java.lang.Math"
            ));
        
        secure.setTokensWhitelist(Arrays.asList(
            Types.PLUS,
            Types.MINUS,
            Types.MULTIPLY,
            Types.DIVIDE,
            Types.MOD,
            Types.POWER,
            Types.PLUS_PLUS,
            Types.MINUS_MINUS,
            Types.COMPARE_EQUAL,
            Types.COMPARE_NOT_EQUAL,
            Types.COMPARE_LESS_THAN,
            Types.COMPARE_LESS_THAN_EQUAL,
            Types.COMPARE_GREATER_THAN,
            Types.COMPARE_GREATER_THAN_EQUAL
            ));
        
        secure.setConstantTypesClassesWhiteList(Arrays.asList(
            Object.class,
            Integer.class,
            Float.class,
            Long.class,
            Double.class,
            BigDecimal.class,
            String.class,
            Integer.TYPE,
            Long.TYPE,
            Float.TYPE,
            Double.TYPE
            ));
        
        secure.setReceiversClassesWhiteList(Arrays.asList(
            Object.class,
            Math.class,
            Integer.class,
            Float.class,
            Double.class,
            Long.class,
            BigDecimal.class,
            String.class
            ));
      //@formatter:on

		secure.setIndirectImportCheckEnabled(true);
		secure.setPackageAllowed(true);

		CompilerConfiguration config = new CompilerConfiguration();
		config.addCompilationCustomizers(secure, importCustomizer);
		return config;
	}

	public String bindingToString(double time, String group1, String group2) {
		return "{ time: " + time + ", group1: " + group1 + ", group2: " + group2 + " }";
	}
}
