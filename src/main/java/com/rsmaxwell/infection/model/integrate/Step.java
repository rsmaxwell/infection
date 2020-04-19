package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.model.Population;
import com.rsmaxwell.infection.model.model.Rates;

public interface Step {

	Rates calculateRates(double t, double h, Population population, Population other, Group group, Connector connector);
}
