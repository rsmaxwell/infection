package com.rsmaxwell.infection.model.config;

import java.util.HashMap;
import java.util.Map;

public class Connectors extends HashMap<Pair, Connector> {

	public Connectors(Map<Pair, Connector> connectors) {
		super(connectors);
	}

	public Connectors() {
	}
}
