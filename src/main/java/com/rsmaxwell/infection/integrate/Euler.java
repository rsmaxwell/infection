package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.model.Population;
import com.rsmaxwell.infection.model.Rates;

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
