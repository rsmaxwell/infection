package com.rsmaxwell.infection.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rsmaxwell.infection.integrate.Integrate;
import com.rsmaxwell.infection.output.Result;
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

	@JsonIgnore
	public List<Result> results = new ArrayList<Result>();

	public void integrate(double t, double dt, Integrate integrate, Connector connector) {
		integrate.step(t, dt, S, I, R, this, connector);
	}

	public void store(double t) {
		results.add(new Result(t, S.value, I.value, R.value));
	}
}
