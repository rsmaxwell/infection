package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.config.Pair;
import com.rsmaxwell.infection.model.engine.Engine;
import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.model.Quantity;

public class Euler extends AbstractIntegrate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public Quantity rate(double t, double dt, Population population, Population other) {

		Group group = population.group;
		Connector connector = Config.INSTANCE.connectors.get(new Pair(population.id, other.id));

		return Engine.INSTANCE.model.rate(t, dt, population.sir, other.sir, group, connector);
	}
}
