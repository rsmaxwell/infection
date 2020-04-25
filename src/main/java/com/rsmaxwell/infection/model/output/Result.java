package com.rsmaxwell.infection.model.output;

import java.io.PrintWriter;

public class Result {

	public double t;
	public double S;
	public double I;
	public double R;

	public Result(double t, double S, double I, double R) {
		this.t = t;
		this.S = S;
		this.I = I;
		this.R = R;
	}

	public void print(PrintWriter out) {
		out.printf("%5.2f     %9.6f     %9.6f     %9.6f\n", t, S, I, R);
	}

	public void toJson(PrintWriter out) {
		out.printf("{ \"t\": %f, \"S\": %.15e, \"I\": %.15e, \"R\": %.15e }", t, S, I, R);
	}
}
