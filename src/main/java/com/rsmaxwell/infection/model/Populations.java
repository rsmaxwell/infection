package com.rsmaxwell.infection.model;

import java.util.ArrayList;
import java.util.List;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Group;

public class Populations {

	List<Population> populations = new ArrayList<Population>();

	public Populations() {
		for (String id : Config.INSTANCE.groups.keySet()) {
			Group group = Config.INSTANCE.groups.get(id);
			populations.add(new Population(id, group));
		}
	}

	public void step(double t) {
		for (Population population : populations) {
			population.step(t);
		}
	}

	public void store(double t) {
		for (Population population : populations) {
			population.store(t);
		}
	}

	public void output() {
		for (Population population : populations) {
			population.output();
		}
	}
}
