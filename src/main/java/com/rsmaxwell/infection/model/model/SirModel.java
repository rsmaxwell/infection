package com.rsmaxwell.infection.model.model;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;

public class SirModel implements Model {

	@Override
	public Quantity rate(double t, double dt, Quantity sir1, Quantity sir2, Group group, Connector connector) {

		double alpha = connector.transmission * sir1.susceptible * sir2.infected;
		double beta = group.recovery * sir1.infected;

		double kS = -alpha;
		double kI = alpha - beta;
		double kR = beta;

		return new Quantity(kS, kI, kR);
	}

}
