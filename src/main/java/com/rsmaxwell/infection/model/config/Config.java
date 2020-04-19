package com.rsmaxwell.infection.model.config;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.rsmaxwell.infection.model.integrate.Integrate;
import com.rsmaxwell.infection.model.integrate.Step;

public class Config {

	public double iStart;
	public double maxTime;
	public double transmission;
	public int resolution;
	public String integrationMethod;
	public double dt;
	public double totalPopulation = 0;
	public Integrate integrate;
	public double sStart;
	public double rStart;
	public Map<String, Group> groups = new HashMap<String, Group>();
	public Map<Pair, Connector> connectors = new HashMap<Pair, Connector>();

	public static Config INSTANCE;

	public Config init() throws Exception {

		// ******************************************************************
		// * Get the integration method
		// ******************************************************************
		Class<?> clazz = Class.forName(integrationMethod);
		Constructor<?> ctor = clazz.getConstructor();
		Object object = ctor.newInstance();

		if (!Step.class.isInstance(object)) {
			throw new Exception("The class [" + integrationMethod + "] does not implement [" + Integrate.class.getName() + "]");
		}

		integrate = (Integrate) object;

		// ******************************************************************
		// * Calculate the initial values, and the totalPopulation
		// ******************************************************************
		dt = 1.0 / resolution;

		for (String id : groups.keySet()) {
			Group group = groups.get(id);
			group.sStart = 1.0 - group.iStart;
			group.rStart = 0;
		}

		totalPopulation = 0.0;
		for (String id : groups.keySet()) {
			Group group = groups.get(id);

			if (group.population < 0) {
				throw new Exception("The population of group [ " + id + " : " + group.name + " ]: cannot be negative: " + group.population);
			}

			totalPopulation += group.population;
		}

		if (totalPopulation < 1e-6) {
			throw new Exception("The totalPopulation cannot be negative: " + totalPopulation);
		}

		// ******************************************************************
		// * Check the connectors match the groups
		// ******************************************************************
		for (String id1 : groups.keySet()) {
			for (String id2 : groups.keySet()) {
				Pair key = new Pair(id1, id2);
				Connector connector = connectors.get(key);
				if (connector == null) {
					throw new Exception("Missing connector: key: " + key);
				}
			}
		}

		for (Pair key : connectors.keySet()) {
			String id = key.one;
			Group group = groups.get(id);
			if (group == null) {
				throw new Exception("Extraneous connector: key: " + key + ", (group " + id + " not found");
			}

			id = key.two;
			group = groups.get(key.two);
			if (group == null) {
				throw new Exception("Extraneous connector: key: " + key + ", (group " + id + " not found");
			}
		}

		if (groups.size() == 0) {
			throw new Exception("There are no groups");
		}

		// ******************************************************************
		// * Update the instance
		// ******************************************************************
		INSTANCE = this;

		return this;
	}
}
