package com.rsmaxwell.infection.model.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connector {

	public double transmission;

	public static Pair validateName(String name) throws Exception {

		Pattern r = Pattern.compile("([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)");
		Matcher m = r.matcher(name);

		if (!m.find()) {
			throw new Exception("connector name must be a group name followed by a '.' followed by another group name");
		}

		return new Pair(m.group(1), m.group(2));
	}
}
