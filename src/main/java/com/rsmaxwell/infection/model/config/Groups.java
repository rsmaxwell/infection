package com.rsmaxwell.infection.model.config;

import java.util.HashMap;
import java.util.Map;

public class Groups extends HashMap<String, Group> {

	public Groups(Map<String, Group> groups) {
		super(groups);
	}

	public Groups() {
	}
}
