package com.rsmaxwell.infection.model;

import com.rsmaxwell.infection.config.Config;

public class Model {

	public void run() {

		Config config = Config.INSTANCE;

		Populations populations = new Populations();

		for (int i = 0; i < config.maxTime * config.resolution; i++) {
			double t = i * config.dt;

			populations.step(t);
			populations.store(t);
		}

		populations.output();
	}
}
