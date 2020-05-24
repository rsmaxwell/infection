package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.engine.Engine;
import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.model.Quantity;

public class Euler extends AbstractIntegrate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public Quantity rate(double t, double dt, Population population, Population other, double recovery, double transmission) {

		return Engine.INSTANCE.model.rate(t, dt, population.sir, other.sir, recovery, transmission);
	}
}
