package com.rsmaxwell.infection.model.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectorNameInfo {

	private boolean isSpecial;
	private List<String> names = new ArrayList<String>();

	private static final Pattern r = Pattern.compile("([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)");
	private static final Set<String> specialNames = new HashSet<String>();

	static {
		specialNames.add("default");
	}

	public ConnectorNameInfo(String name) throws Exception {
		if (specialNames.contains(name)) {
			isSpecial = true;
		} else {
			Matcher m = r.matcher(name);

			if (!m.find()) {
				throw new Exception("connector name must be a group name followed by a '.' followed by another group name");
			}

			names.add(m.group(1));
			names.add(m.group(2));
		}
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public String getName(int index) {
		return names.get(index);
	}
}
