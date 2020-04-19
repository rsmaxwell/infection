package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.config.Pair;
import com.rsmaxwell.infection.model.model.Population;
import com.rsmaxwell.infection.model.model.Populations;
import com.rsmaxwell.infection.model.model.Rates;

abstract class AbstractIntegrate implements Integrate, Step {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public void step(double t, double h, Population population) {

		Config config = Config.INSTANCE;
		Populations populations = Populations.INSTANCE;

		String id = population.id;
		Group group = population.group;

		double dSdt = 0;
		double dIdt = 0;
		double dRdt = 0;

		for (String id2 : populations.populations.keySet()) {
			Population other = populations.populations.get(id2);
			Connector connector = config.connectors.get(new Pair(id, id2));

			double factor = other.group.population / Config.INSTANCE.totalPopulation;

			Rates rates = calculateRates(t, h, population, other, group, connector);

			dSdt += factor * rates.kS;
			dIdt += factor * rates.kI;
			dRdt += factor * rates.kR;
		}

		population.S.delta = dSdt * h;
		population.I.delta = dIdt * h;
		population.R.delta = dRdt * h;
	}
}
