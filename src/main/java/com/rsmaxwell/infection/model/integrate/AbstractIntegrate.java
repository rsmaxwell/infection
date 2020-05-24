package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.engine.Populations;
import com.rsmaxwell.infection.model.model.Quantity;

abstract class AbstractIntegrate implements Integrate, Rate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public void step(double t, double dt, Population population, Populations populations, double totalPopulation) throws Exception {

		double recovery = population.group.recovery;

		Quantity totalRates = new Quantity();

		for (Population other : populations.populations.values()) {
			double factor = other.group.population / totalPopulation;

			String key = population.id + "." + other.id;
			Connector connector = Config.INSTANCE.connectors.get(key);
			if (connector == null) {
				connector = Config.INSTANCE.connectors.get("default");
			}

			double transmission = connector.getTransmission(t, population.id, other.id);

			Quantity rates = rate(t, dt, population, other, recovery, transmission);
			totalRates = rates.multiply(factor).add(totalRates);
		}

		population.delta = totalRates.multiply(dt);
	}
}
