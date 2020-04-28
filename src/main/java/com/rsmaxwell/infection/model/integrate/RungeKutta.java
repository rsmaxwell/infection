package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.config.Pair;
import com.rsmaxwell.infection.model.engine.Engine;
import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.model.Model;
import com.rsmaxwell.infection.model.model.Quantity;

public class RungeKutta extends AbstractIntegrate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public Quantity rate(double t, double h, Population population, Population otherPopulation) {

		Model model = Engine.INSTANCE.model;
		Group group = population.group;
		Connector connector = Config.INSTANCE.connectors.get(new Pair(population.id, otherPopulation.id));
		Quantity other = otherPopulation.sir;
		Quantity sir1 = population.sir;

		Quantity k1 = model.rate(t, h, sir1, other, group, connector);

		Quantity sir2 = k1.multiply(0.5 * h).add(population.sir);
		double t2 = t + h / 2.0;
		Quantity k2 = model.rate(t2, h, sir2, other, group, connector);

		Quantity sir3 = k2.multiply(0.5 * h).add(sir2);
		double t3 = t + h / 2.0;
		Quantity k3 = model.rate(t3, h, sir3, other, group, connector);

		Quantity sir4 = k2.multiply(0.5 * h).add(sir3);
		double t4 = t + h / 2.0;
		Quantity k4 = model.rate(t4, h, sir4, other, group, connector);

		return k2.add(k3).multiply(2.0).add(k1).add(k4).divide(6.0);
	}
}
