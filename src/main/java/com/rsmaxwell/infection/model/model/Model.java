package com.rsmaxwell.infection.model.model;

import com.rsmaxwell.infection.model.config.Connector;
import com.rsmaxwell.infection.model.config.Group;

public interface Model {

	Quantity rate(double t, double dt, Quantity sir1, Quantity sir2, Group group, Connector connector);

}
