package com.rsmaxwell.infection.model.config;

public class Group {

	public String name;
	public double recovery;
	public double population;
	public double iStart;

	public static String validateName(String name) throws Exception {

		if (!name.matches("[a-zA-Z0-9]+")) {
			throw new Exception("group name must only contain alphanumeric chars");
		}

		return name;
	}
}
