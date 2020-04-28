package com.rsmaxwell.infection.model.model;

public class Quantity {

	public double susceptible;
	public double infected;
	public double recovered;

	public Quantity() {
	}

	public Quantity(double susceptible, double infected, double recovered) {
		this.susceptible = susceptible;
		this.infected = infected;
		this.recovered = recovered;
	}

	public Quantity(Quantity value) {
		this.susceptible = value.susceptible;
		this.infected = value.infected;
		this.recovered = value.recovered;
	}

	public Quantity zero() {
		this.susceptible = 0;
		this.infected = 0;
		this.recovered = 0;
		return this;
	}

	public Quantity multiply(double factor) {
		Quantity result = new Quantity(this);
		result.susceptible *= factor;
		result.infected *= factor;
		result.recovered *= factor;
		return result;
	}

	public Quantity divide(double factor) {
		Quantity result = new Quantity(this);
		result.susceptible /= factor;
		result.infected /= factor;
		result.recovered /= factor;
		return result;
	}

	public Quantity add(Quantity value) {
		Quantity result = new Quantity(this);
		result.susceptible += value.susceptible;
		result.infected += value.infected;
		result.recovered += value.recovered;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ ");
		sb.append(susceptible + ", ");
		sb.append(infected + ", ");
		sb.append(recovered);
		sb.append(" }");
		return sb.toString();
	}
}
