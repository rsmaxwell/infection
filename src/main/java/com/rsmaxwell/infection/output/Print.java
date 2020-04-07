package com.rsmaxwell.infection.output;

import java.util.List;

public class Print implements Output {

	@Override
	public void print(String name, List<Result> results) {
		System.out.println("---[ " + name + " ]-----------------------------------------------");
		System.out.println("");
		System.out.println(" time    Susceptible       Infected      Recovered");
		System.out.println("--------------------------------------------------");

		for (int i = 0; i < results.size(); i++) {
			Result result = results.get(i);

			long iPart = (long) result.t;
			double fPart = result.t - iPart;

			if (fPart < 0.0001) {
				System.out.printf("%5.2f          %5.2f          %5.2f          %5.2f\n", result.t, result.S, result.I, result.R);
			}
		}
	}
}
