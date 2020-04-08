package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.model.Population;

public interface Integrate {

	void step(double t, double dt, Population population);
}
