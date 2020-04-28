package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.engine.Populations;
import com.rsmaxwell.infection.model.model.Quantity;

abstract class AbstractIntegrate implements Integrate, Rate {

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	@Override
	public void step(double t, double dt, Population population, Populations populations, double totalPopulation) {

		Quantity totalRates = new Quantity();

		for (Population other : populations.populations.values()) {

			int x = 0;
			double factor = other.group.population / totalPopulation;

			if ("2".equals(population.id) && "1".equals(other.id)) {
				x = 1;
			}

			Quantity rates = rate(t, dt, population, other);

			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%-30s", "AbstractIntegrate.step(1):"));
			sb.append(String.format("%-30s", "population: " + population.group.name + "(" + population.id + ")"));
			sb.append(String.format("%-30s", "other: " + other.group.name + "(" + other.id + ")"));
			sb.append("rates: " + rates);
			System.err.println(sb.toString());

			totalRates = rates.multiply(factor).add(totalRates);
		}

		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%-30s", "AbstractIntegrate.step(2):"));
		sb.append(String.format("%-30s", "population: " + population.group.name + "(" + population.id + ")"));
		sb.append("totalRates: " + totalRates);
		System.err.println(sb.toString());

		sb = new StringBuffer();
		sb.append(String.format("%-30s", "AbstractIntegrate.step(3):"));
		sb.append(String.format("%-30s", "population: " + population.group.name + "(" + population.id + ")"));
		sb.append("population.delta: " + population.delta + ", ");
		System.err.println(sb.toString());

		population.delta = totalRates.multiply(dt);

		sb = new StringBuffer();
		sb.append(String.format("%-30s", "AbstractIntegrate.step(4): "));
		sb.append(String.format("%-30s", "population: " + population.group.name + "(" + population.id + ")"));
		sb.append("population.delta: " + population.delta);
		System.err.println(sb.toString());
	}
}
