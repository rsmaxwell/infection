package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.quantity.Quantity;

public class RungeKutta implements Integrate {

	@Override
	public void step(double t, double h, Quantity S, Quantity I, Quantity R) {

		double t2 = t + h / 2.0;
		double t3 = t + h / 2.0;
		double t4 = t + h;

		double k1S = S.rate(t, S.value, I.value, R.value);
		double k1I = I.rate(t, S.value, I.value, R.value);
		double k1R = R.rate(t, S.value, I.value, R.value);

		double S2 = S.value + h * k1S / 2.0;
		double I2 = I.value + h * k1I / 2.0;
		double R2 = R.value + h * k1R / 2.0;

		double k2S = S.rate(t2, S2, I2, R2);
		double k2I = I.rate(t2, S2, I2, R2);
		double k2R = R.rate(t2, S2, I2, R2);

		double S3 = S.value + h * k2S / 2.0;
		double I3 = I.value + h * k2I / 2.0;
		double R3 = R.value + h * k2R / 2.0;

		double k3S = S.rate(t3, S3, I3, R3);
		double k3I = I.rate(t3, S3, I3, R3);
		double k3R = R.rate(t3, S3, I3, R3);

		double S4 = S.value + h * k3S;
		double I4 = I.value + h * k3I;
		double R4 = R.value + h * k3R;

		double k4S = S.rate(t4, S4, I4, R4);
		double k4I = I.rate(t4, S4, I4, R4);
		double k4R = R.rate(t4, S4, I4, R4);

		S.add(h * (k1S + 2.0 * (k2S + k3S) + k4S) / 6.0);
		I.add(h * (k1I + 2.0 * (k2I + k3I) + k4I) / 6.0);
		R.add(h * (k1R + 2.0 * (k2R + k3R) + k4R) / 6.0);
	}
}
