package com.rsmaxwell.infection.output;

import java.util.List;

public class Print implements Output {

	@Override
	public void print(String name, List<Result> results) {
		System.out.println("---[ " + name + " ]-----------------------------------------------");
		System.out.println("");
		System.out.println(" time        Susceptible           Infected          Recovered");
		System.out.println("--------------------------------------------------------------");

		Result max = null;

		for (int i = 0; i < results.size(); i++) {
			Result result = results.get(i);

			// long iPart = (long) result.t;
			// double fPart = result.t - iPart;

			// if (fPart < 0.0001) {
			System.out.printf("%5.2f          %9.6f          %9.6f          %9.6f\n", result.t, result.S, result.I, result.R);
			// }

			if (max == null) {
				max = result;
			} else if (max.I < result.I) {
				max = result;
			}
		}

		System.out.println("");
		System.out.println("Maximium Infection:");
		System.out.printf("%5.2f          %9.6f          %9.6f          %9.6f\n", max.t, max.S, max.I, max.R);
	}
}
