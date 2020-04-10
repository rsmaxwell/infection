package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.model.Population;

public interface Integrate {

	public void step(double t, double h, Population population);

}
