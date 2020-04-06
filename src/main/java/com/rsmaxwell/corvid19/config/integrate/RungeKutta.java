package com.rsmaxwell.corvid19.config.integrate;

import com.rsmaxwell.corvid19.config.quantity.Quantity;

public class RungeKutta implements Integrate {

	@Override
	public void step(double t, double h, Quantity S, Quantity I, Quantity R) {

		double k1, k2, k3, k4;
		double S2, I2, R2;
		double S3, I3, R3;
		double S4, I4, R4;

		double t2 = t + h / 2.0;
		double t3 = t + h / 2.0;
		double t4 = t + h;

		// ----------------------------------------------------------
		k1 = S.rate(t, S.value, I.value, R.value);

		S2 = S.value + h * k1 / 2.0;
		k2 = S.rate(t2, S2, I.value, R.value);

		S3 = S.value + h * k1 / 2.0;
		k3 = S.rate(t3, S3, I.value, R.value);

		S4 = S.value + h * k1;
		k4 = S.rate(t4, S4, I.value, R.value);

		S.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);

		// ----------------------------------------------------------
		k1 = I.rate(t, S.value, I.value, R.value);

		I2 = I.value + h * k1 / 2.0;
		k2 = I.rate(t2, S.value, I2, R.value);

		I3 = I.value + h * k1 / 2.0;
		k3 = I.rate(t3, S.value, I3, R.value);

		I4 = I.value + h * k1;
		k4 = I.rate(t4, S.value, I4, R.value);

		I.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);

		// ----------------------------------------------------------
		k1 = R.rate(t, S.value, I.value, R.value);

		R2 = R.value + h * k1 / 2.0;
		k2 = R.rate(t2, S.value, I.value, R2);

		R3 = R.value + h * k1 / 2.0;
		k3 = R.rate(t3, S.value, I.value, R3);

		R4 = R.value + h * k1;
		k4 = R.rate(t4, S.value, I.value, R4);

		R.add(h * (k1 + 2.0 * (k2 + k3) + k4) / 6.0);
	}
}
