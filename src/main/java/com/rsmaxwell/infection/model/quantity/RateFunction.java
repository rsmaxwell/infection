package com.rsmaxwell.infection.model.quantity;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;

public interface RateFunction {

	double rate(double t, double S, double I, double R, Group group, Connector connector);
}
