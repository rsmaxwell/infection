package com.rsmaxwell.infection.config;

public class Parameters {

	public double transmission = 0;
	public double recovery = 0;

	public Parameters(double transmission, double recovery) {
		this.transmission = transmission;
		this.recovery = recovery;
	}

	@Override
	public String toString() {
		return "{ \"transmission\": " + transmission + ", \"recovery\": " + recovery + " }";
	}
}
