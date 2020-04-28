package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.engine.Populations;
import com.rsmaxwell.infection.model.model.Quantity;

abstract class AbstractIntegrate implements Integrate, Rate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public void step(double t, double dt, Population population, Populations populations, double totalPopulation) {

		Quantity totalRates = new Quantity();

		for (Population other : populations.populations.values()) {
			double factor = other.group.population / totalPopulation;
			Quantity rates = rate(t, dt, population, other);
			totalRates = rates.multiply(factor).add(totalRates);
		}

		population.delta = totalRates.multiply(dt);
	}
}
