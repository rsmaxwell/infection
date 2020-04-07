package com.rsmaxwell.infection.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rsmaxwell.infection.integrate.Integrate;
import com.rsmaxwell.infection.quantity.Quantity;

public class Group {

	public String name;
	public double recovery;
	public double population;
	public double iStart;
	public double rStart;

	@JsonIgnore
	public double sStart;

	@JsonIgnore
	public Quantity S;

	@JsonIgnore
	public Quantity I;

	@JsonIgnore
	public Quantity R;

	@JsonIgnore
	public double N;

	public void heading() {
		System.out.printf(" time     Suseptible       Infected      Recovered\n");
		System.out.printf("--------------------------------------------------\n");
	}

	public void output(double t) {
		System.out.printf("%5.2f          %5.2f          %5.2f          %5.2f\n", t, S.value, I.value, R.value);
	}

	public void integrate(double t, double dt, Integrate integrate, Connector connector) {
		integrate.step(t, dt, S, I, R, this, connector);
	}
}
