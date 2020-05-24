package com.rsmaxwell.infection.model.model;

public interface Model {

	Quantity rate(double t, double dt, Quantity sir1, Quantity sir2, double recovery, double transmission);

}
