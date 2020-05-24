package com.rsmaxwell.infection.model.app;

import java.security.Permission;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionPattern {

	private Pattern perm;
	private Pattern name;
	private Pattern action;

	public PermissionPattern(String perm, String name, String action) {
		this.perm = Pattern.compile(perm);
		this.name = Pattern.compile(name);
		this.action = Pattern.compile(action);
	}

	public boolean matches(Permission permission) {

		Matcher matchPerm = perm.matcher(permission.getClass().getName());
		if (!matchPerm.matches()) {
			return false;
		}

		Matcher matchName = name.matcher(permission.getName());
		if (!matchName.matches()) {
			return false;
		}

		Matcher matchAction = action.matcher(permission.getActions());
		if (!matchAction.matches()) {
			return false;
		}

		return true;
	}
}
