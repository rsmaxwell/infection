package com.rsmaxwell.infection.quantity;

import com.rsmaxwell.infection.config.Connector;
import com.rsmaxwell.infection.config.Group;

public interface RateFunction {

	double rate(double t, double S, double I, double R, Group group, Connector connector);
}
