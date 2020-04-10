package com.rsmaxwell.infection.integrate;

import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.model.Population;
import com.rsmaxwell.infection.model.Rates;

public interface Step {

	Rates calculateRates(double t, double h, Population population, Population other, Group group, Connector connector);
}
