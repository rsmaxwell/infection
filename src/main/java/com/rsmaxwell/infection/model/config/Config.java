package com.rsmaxwell.infection.model.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Config {

	public double maxTime;
	public int resolution;
	public String integrationMethod;
	public Groups groups = new Groups();
	public Connectors connectors = new Connectors();
	public Map<String, Object> output = new HashMap<String, Object>();

	public static Config INSTANCE;

	public static Config load(String json) throws Exception {
		Config config = null;
		Gson gson = new GsonBuilder().registerTypeAdapter(Connectors.class, new ConnectorsDeserializer()).create();

		try {
			config = gson.fromJson(json, Config.class);
		} catch (JsonSyntaxException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.printf(e.getMessage());
			pw.println();
			String lines[] = json.split("\\r?\\n");
			for (int i = 0; i < lines.length; i++) {
				pw.printf("%3d:   %s\n", i + 1, lines[i]);
			}
			throw new ConfigSyntaxException(sw.getBuffer().toString(), e);
		}

		// ******************************************************************
		// * Update and validate the instance
		// ******************************************************************
		config.setInstance();

		return config.validate();
	}

	public void setInstance() throws Exception {
		INSTANCE = this;
	}

	// ******************************************************************
	// * Check the connectors match the groups
	// ******************************************************************
	public Config validate() throws Exception {

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

		return this;
	}
}
