package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.model.Population;
import com.rsmaxwell.infection.model.model.Rates;

public class Euler extends AbstractIntegrate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public Rates calculateRates(double t, double h, Population population, Population other, Group group, Connector connector) {

		double kS = population.S.rate(t, other.S.value, other.I.value, other.R.value, group, connector);
		double kI = population.I.rate(t, other.S.value, other.I.value, other.R.value, group, connector);
		double kR = population.R.rate(t, other.S.value, other.I.value, other.R.value, group, connector);

		return new Rates(kS, kI, kR);
	}
}
