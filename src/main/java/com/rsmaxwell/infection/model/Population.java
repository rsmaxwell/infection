package com.rsmaxwell.infection.model;

import java.util.ArrayList;
import java.util.List;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.config.Group;
import com.rsmaxwell.infection.output.Result;
import com.rsmaxwell.infection.quantity.Infected;
import com.rsmaxwell.infection.quantity.Quantity;
import com.rsmaxwell.infection.quantity.Recovered;
import com.rsmaxwell.infection.quantity.Susceptible;

public class Population {

	public String id;
	public Group group;

	public Quantity S;
	public Quantity I;
	public Quantity R;

	public List<Result> results = new ArrayList<Result>();

	public Population(String id, Group group) {
		this.id = id;
		this.group = group;

		double factor = group.population / Config.INSTANCE.totalPopulation;

		S = new Susceptible(group.sStart * factor);
		I = new Infected(group.iStart * factor);
		R = new Recovered(group.rStart * factor);
	}

	public void store(double t) {
		results.add(new Result(t, S.value, I.value, R.value));
	}

	public void step(double t) {
		Config.INSTANCE.integrate.step(t, Config.INSTANCE.dt, this);
	}

	public void output() {
		Config.INSTANCE.output.print(group.name, results);
	}
}
