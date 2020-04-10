package com.rsmaxwell.infection.model;

import java.util.HashMap;
import java.util.Map;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Group;

public class Populations {

	public Map<String, Population> populations = new HashMap<String, Population>();
	public Population everyone;

	public static Populations INSTANCE;

	public Populations() {
		for (String id : Config.INSTANCE.groups.keySet()) {
			Group group = Config.INSTANCE.groups.get(id);
			populations.put(id, new Population(id, group));
		}

		Group all = new Group();
		all.name = "Everyone";

		everyone = new Population(null, all);
		INSTANCE = this;
	}

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	//
	// This is because ALL the current SIR values are used to calculate the SIR
	// deltas for each population, so they must not be changed till we know all the
	// deltas
	public void step(double t) {

		// Calculate the SIR deltas for this step
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.step(t);
		}

		// Add the SIR deltas to the SIR values
		for (String id : populations.keySet()) {
			Population population = populations.get(id);

			population.S.value += population.S.delta;
			population.I.value += population.I.delta;
			population.R.value += population.R.delta;
		}
	}

	public void store(double t) {

		everyone.zero();

		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.store(t);

			everyone.add(population);
		}

		everyone.store(t);
	}

	public void output() {
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.output();
		}

		if (populations.size() > 1) {
			everyone.output();
		}
	}
}
