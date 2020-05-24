package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.engine.Populations;

public interface Integrate {

	public void step(double t, double dt, Population population, Populations populations, double totalPopulation) throws Exception;

}
