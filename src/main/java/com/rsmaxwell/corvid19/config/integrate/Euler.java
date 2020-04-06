package com.rsmaxwell.corvid19.config.integrate;

import com.rsmaxwell.corvid19.config.quantity.Quantity;

public class Euler implements Integrate {

	@Override
	public void step(double t, double dt, Quantity S, Quantity I, Quantity R) {

		/* calculate derivatives */
		double dSdt = S.rate(t, S.value, I.value, R.value);
		double dIdt = I.rate(t, S.value, I.value, R.value);
		double dRdt = R.rate(t, S.value, I.value, R.value);

		/* now integrate using Euler */
		S.add(dSdt * dt);
		I.add(dIdt * dt);
		R.add(dRdt * dt);
	}
}
