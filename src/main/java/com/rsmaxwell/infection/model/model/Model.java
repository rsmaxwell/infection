package com.rsmaxwell.infection.model.model;

import com.rsmaxwell.infection.model.config.Config;

public class Model {

	private Config config;

	public Model(Config config) {
		this.config = config;
	}

	public Populations run() {

		Populations populations = new Populations();

		for (int i = 0; i < config.maxTime * config.resolution; i++) {
			double t = i * config.dt;

			populations.step(t);
			populations.store(t);
		}

		return populations;
	}
}
