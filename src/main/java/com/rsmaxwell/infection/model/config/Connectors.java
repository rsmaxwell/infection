package com.rsmaxwell.infection.model.config;

import java.util.HashMap;
import java.util.Map;

public class Connectors extends HashMap<String, Connector> {

	public Connectors(Map<String, Connector> connectors) {
		super(connectors);
	}

	public Connectors() {
	}
}
