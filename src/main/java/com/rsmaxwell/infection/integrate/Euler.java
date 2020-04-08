package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.config.Pair;
import com.rsmaxwell.infection.model.Population;
import com.rsmaxwell.infection.model.Populations;

public class Euler implements Integrate {

	@Override
	public void step(double t, double dt, Population population) {

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

			dSdt += population.S.rate(t, other.S.value, other.I.value, other.R.value, group, connector);
			dIdt += population.I.rate(t, other.S.value, other.I.value, other.R.value, group, connector);
			dRdt += population.R.rate(t, other.S.value, other.I.value, other.R.value, group, connector);
		}

		population.S.add(dSdt * dt);
		population.I.add(dIdt * dt);
		population.R.add(dRdt * dt);
	}
}
