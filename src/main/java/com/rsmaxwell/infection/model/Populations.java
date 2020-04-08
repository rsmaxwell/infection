package com.rsmaxwell.infection.model;

import java.util.HashMap;
import java.util.Map;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Group;

public class Populations {

	public Map<String, Population> populations = new HashMap<String, Population>();

	public static Populations INSTANCE;

	public Populations() {
		for (String id : Config.INSTANCE.groups.keySet()) {
			Group group = Config.INSTANCE.groups.get(id);
			populations.put(id, new Population(id, group));
		}

		INSTANCE = this;
	}

	public void step(double t) {
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.step(t);
		}
	}

	public void store(double t) {
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.store(t);
		}
	}

	public void output() {
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.output();
		}
	}
}
