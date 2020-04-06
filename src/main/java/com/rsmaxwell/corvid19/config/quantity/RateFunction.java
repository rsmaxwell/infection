package com.rsmaxwell.corvid19.config.quantity;

public interface RateFunction {

	double rate(double t, double S, double I, double R);
}
