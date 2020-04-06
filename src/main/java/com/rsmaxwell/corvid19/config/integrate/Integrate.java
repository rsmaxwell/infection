package com.rsmaxwell.corvid19.config.integrate;

import com.rsmaxwell.corvid19.config.quantity.Quantity;

public interface Integrate {

	void step(double t, double dt, Quantity S, Quantity I, Quantity R);
}
