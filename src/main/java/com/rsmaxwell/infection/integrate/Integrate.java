package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.quantity.Quantity;

public interface Integrate {

	void step(double t, double dt, Quantity S, Quantity I, Quantity R);
}
