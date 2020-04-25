package com.rsmaxwell.infection.model.model;

import java.lang.reflect.Constructor;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.integrate.Integrate;
import com.rsmaxwell.infection.model.integrate.Step;

public class Model {

	private Config config;
	public double dt;
	public Integrate integrate;

	public Model(Config config) throws Exception {
		this.config = config;

		// ******************************************************************
		// * Get the integration method
		// ******************************************************************
		Class<?> clazz = Class.forName(config.integrationMethod);
		Constructor<?> ctor = clazz.getConstructor();
		Object object = ctor.newInstance();

		if (!Step.class.isInstance(object)) {
			throw new Exception("The class [" + config.integrationMethod + "] does not implement [" + Integrate.class.getName() + "]");
		}

		integrate = (Integrate) object;

		// ******************************************************************
		// * Calculate the initial values
		// ******************************************************************
		dt = 1.0 / config.resolution;
	}

	public Populations run() throws Exception {

		Populations populations = new Populations();

		for (int i = 0; i < config.maxTime * config.resolution; i++) {
			double t = i * dt;

			populations.step(t, dt, integrate);
			populations.store(t);
		}

		return populations;
	}
}
