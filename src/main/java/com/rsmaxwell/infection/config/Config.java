package com.rsmaxwell.infection.config;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

public class Config {

	public double N;
	public double iStart;
	public double rStart;
	public double maxTime;
	public int resolution;

	@JsonIgnore
	public double sStart;

	@JsonIgnore
	public Map<String, Group> groups = new HashMap<String, Group>();

	@JsonIgnore
	public Map<Pair, Connector> connectors = new HashMap<Pair, Connector>();

	static public Config load(String dirname) throws Exception {
		Config config = null;
		Gson gson = new Gson();

		// ******************************************************************
		// * Read the overall configuration
		// ******************************************************************
		String filename = Paths.get(dirname, "config.json").toString();
		try {
			config = gson.fromJson(new FileReader(filename), Config.class);
		} catch (Exception e) {
			throw new Exception(filename, e);
		}
		config.sStart = config.N - config.iStart;

		// ******************************************************************
		// * Read the population groups
		// ******************************************************************
		String groupDir = Paths.get(dirname, "groups").toString();
		File dir = new File(groupDir);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) {
			throw new Exception("The are no groups");
		}

		try {
			for (File child : directoryListing) {
				String id = getGroupId(child);

				filename = Paths.get(child.getCanonicalPath(), "config.json").toString();

				Group group = gson.fromJson(new FileReader(filename), Group.class);

				config.groups.put(id, group);
			}
		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		// ******************************************************************
		// * Read the group connectors
		// ******************************************************************
		String connectorDir = Paths.get(dirname, "connectors").toString();
		dir = new File(connectorDir);
		directoryListing = dir.listFiles();
		if (directoryListing == null) {
			throw new Exception("The are no connectors");
		}

		try {
			for (File child : directoryListing) {
				Pair id = getConnectorId(child);

				filename = Paths.get(child.getCanonicalPath(), "config.json").toString();

				Connector connector = gson.fromJson(new FileReader(filename), Connector.class);

				config.connectors.put(id, connector);
			}
		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		// ******************************************************************
		// * Check the connectors match the groups
		// ******************************************************************
		for (String id1 : config.groups.keySet()) {
			for (String id2 : config.groups.keySet()) {
				Pair key = new Pair(id1, id2);
				Connector connector = config.connectors.get(key);
				if (connector == null) {
					throw new Exception("Missing connector: key: " + key);
				}
			}
		}

		for (Pair key : config.connectors.keySet()) {
			String id = key.one;
			Group group = config.groups.get(id);
			if (group == null) {
				throw new Exception("Extraneous connector: key: " + key + ", (group " + id + " not found");
			}

			id = key.two;
			group = config.groups.get(key.two);
			if (group == null) {
				throw new Exception("Extraneous connector: key: " + key + ", (group " + id + " not found");
			}
		}

		if (config.groups.size() == 0) {
			throw new Exception("There are no groups");
		}

		return config;
	}

	private static String getGroupId(File file) throws Exception {

		String name = file.getName();

		if (!name.matches("[a-zA-Z0-9]+")) {
			throw new Exception("group name must only contain alphanumeric chars");
		}

		return name;
	}

	private static Pair getConnectorId(File file) throws Exception {

		String name = file.getName();

		Pattern r = Pattern.compile("([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)");
		Matcher m = r.matcher(name);

		if (!m.find()) {
			throw new Exception("connector name must be a group name followed by a '.' followed by another group name");
		}

		return new Pair(m.group(1), m.group(2));
	}
}
