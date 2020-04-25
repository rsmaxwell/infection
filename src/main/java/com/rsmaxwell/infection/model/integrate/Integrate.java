package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.model.Population;

public interface Integrate {

	public void step(double t, double h, Population population, double totalPopulation);

}
