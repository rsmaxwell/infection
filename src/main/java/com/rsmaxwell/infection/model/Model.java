package com.rsmaxwell.infection.model;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.config.Pair;
import com.rsmaxwell.infection.quantity.Infected;
import com.rsmaxwell.infection.quantity.Recovered;
import com.rsmaxwell.infection.quantity.Susceptible;

public class Model {

	private Config config;

	public Model(Config config) {
		this.config = config;
	}

	public void run() {

		double t = 0;
		double dt = 1.0 / config.resolution;

		for (String id : config.groups.keySet()) {
			Group group = config.groups.get(id);
			group.S = new Susceptible(group.sStart);
			group.I = new Infected(group.iStart);
			group.R = new Recovered(group.rStart);
		}

		for (String id : config.groups.keySet()) {
			Group group = config.groups.get(id);
			group.heading();
			group.output(t);
		}

		for (int j = 0; j < config.maxTime; j++) {
			for (int k = 0; k <= config.resolution; k++) {
				int i = config.resolution * j + k;

				t = i * dt;

				for (String id : config.groups.keySet()) {
					Group group = config.groups.get(id);
					Pair key = new Pair(id, id);
					Connector connector = config.connectors.get(key);
					group.integrate(t, dt, config.integrate, connector);
				}
			}

			for (String id : config.groups.keySet()) {
				Group group = config.groups.get(id);
				group.output(t);
			}
		}
	}
}
