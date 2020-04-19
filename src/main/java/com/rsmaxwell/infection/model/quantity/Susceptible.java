package com.rsmaxwell.infection.model.quantity;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;

public class Susceptible extends Quantity {

	public Susceptible(double value) {
		super(value);
	}

	@Override
	public double rate(double t, double S, double I, double R, Group group, Connector connector) {
		return -connector.transmission * S * I;
	}

}
