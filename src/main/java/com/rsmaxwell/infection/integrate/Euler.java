package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.quantity.Quantity;

public class Euler implements Integrate {

	@Override
	public void step(double t, double dt, Quantity S, Quantity I, Quantity R, Group group, Connector connector) {

		/* calculate derivatives */
		double dSdt = S.rate(t, S.value, I.value, R.value, group, connector);
		double dIdt = I.rate(t, S.value, I.value, R.value, group, connector);
		double dRdt = R.rate(t, S.value, I.value, R.value, group, connector);

		/* now integrate using Euler */
		S.add(dSdt * dt);
		I.add(dIdt * dt);
		R.add(dRdt * dt);
	}
}
