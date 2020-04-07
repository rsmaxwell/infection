package com.rsmaxwell.infection.model;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.quantity.Infected;
import com.rsmaxwell.infection.quantity.Quantity;
import com.rsmaxwell.infection.quantity.Recovered;
import com.rsmaxwell.infection.quantity.Suseptible;

public class Model {

	private Config config;

	public static void heading() {
		System.out.printf(" time     Suseptible       Infected      Recovered\n");
		System.out.printf("--------------------------------------------------\n");
	}

	public static void output(double t, Quantity S, Quantity I, Quantity R) {
		System.out.printf("%5.2f          %5.2f          %5.2f          %5.2f\n", t, S.value, I.value, R.value);
	}

	public Model(Config config) {
		this.config = config;
	}

	public void run() {

		double t = 0;

		double dt = 1.0 / config.resolution;
		Quantity S = new Suseptible(config.sStart);
		Quantity I = new Infected(config.iStart);
		Quantity R = new Recovered(config.rStart);

		heading();
		output(t, S, I, R);

		for (int j = 0; j < config.maxTime; j++) {
			for (int k = 0; k <= config.resolution; k++) {
				int i = config.resolution * j + k;

				t = i * dt;

				config.integrate.step(t, dt, S, I, R);
			}

			output(t, S, I, R);
		}
	}
}
