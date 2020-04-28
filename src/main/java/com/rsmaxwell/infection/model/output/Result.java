package com.rsmaxwell.infection.model.output;

import java.io.PrintWriter;

import com.rsmaxwell.infection.model.model.Quantity;

public class Result {

	public double t;
	public Quantity sir;

	public Result(double t, Quantity sir) {
		this.t = t;
		this.sir = new Quantity(sir);
	}

	public void print(PrintWriter out) {
		out.printf("%5.2f     %9.6f     %9.6f     %9.6f\n", t, sir.susceptible, sir.infected, sir.recovered);
	}

	public void toJson(PrintWriter out) {
		out.printf("{ \"t\": %f, \"S\": %.15e, \"I\": %.15e, \"R\": %.15e }", t, sir.susceptible, sir.infected, sir.recovered);
	}
}
