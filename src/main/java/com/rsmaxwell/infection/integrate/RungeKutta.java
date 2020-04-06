package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.quantity.Quantity;

public class RungeKutta implements Integrate {

	@Override
	public void step(double t, double h, Quantity S, Quantity I, Quantity R) {

		double t2 = t + h / 2.0;
		double t3 = t + h / 2.0;
		double t4 = t + h;

		double k1 = S.rate(t, S.value, I.value, R.value);

		double S2 = S.value + h * k1 / 2.0;
		double I2 = I.value + h * k1 / 2.0;
		double R2 = R.value + h * k1 / 2.0;
		double k2 = S.rate(t2, S2, I2, R2);

		double S3 = S.value + h * k1 / 2.0;
		double I3 = I.value + h * k1 / 2.0;
		double R3 = R.value + h * k1 / 2.0;
		double k3 = S.rate(t3, S3, I3, R3);

		double S4 = S.value + h * k1;
		double I4 = I.value + h * k1;
		double R4 = R.value + h * k1;
		double k4 = S.rate(t4, S4, I4, R4);

		S.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);
		I.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);
		R.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);
	}
}
