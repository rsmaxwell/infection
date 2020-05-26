package com.rsmaxwell.infection.model.engine;

import java.lang.reflect.Constructor;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.handler.ResponseCollectorInterface;
import com.rsmaxwell.infection.model.integrate.Integrate;
import com.rsmaxwell.infection.model.model.Model;
import com.rsmaxwell.infection.model.model.SirModel;

public class Engine {

	private Config config;
	public double dt;
	public Integrate integrate;
	public Model model = new SirModel();

	public static Engine INSTANCE;

	public Engine(Config config) throws Exception {
		this.config = config;

		// ******************************************************************
		// * Get the integration method
		// ******************************************************************
		Class<?> clazz = Class.forName(config.integrationMethod);
		Constructor<?> ctor = clazz.getConstructor();
		Object object = ctor.newInstance();

		if (!Integrate.class.isInstance(object)) {
			throw new Exception("The class [" + config.integrationMethod + "] does not implement [" + Integrate.class.getName() + "]");
		}

		integrate = (Integrate) object;

		// ******************************************************************
		// * Calculate the initial values
		// ******************************************************************
		dt = 1.0 / config.resolution;

		// ******************************************************************
		// * Set the instance
		// ******************************************************************
		INSTANCE = this;
	}

	public void run(ResponseCollectorInterface response) throws Exception {

		Populations populations = new Populations();

		for (int i = 0; i < config.maxTime * config.resolution; i++) {
			double t = i * dt;

			populations.step(t, dt);
			populations.store(t);
		}

		config.handler.format(response, populations);
	}
}
