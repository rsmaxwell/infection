package com.rsmaxwell.infection.model.model;

public class SirModel implements Model {

	@Override
	public Quantity rate(double t, double dt, Quantity sir1, Quantity sir2, double recovery, double transmission) {

		double alpha = transmission * sir1.susceptible * sir2.infected;
		double beta = recovery * sir1.infected;

		double kS = -alpha;
		double kI = alpha - beta;
		double kR = beta;

		return new Quantity(kS, kI, kR);
	}

}
