package com.rsmaxwell.infection.model.handler;

import java.util.ArrayList;
import java.util.Map;

public abstract class Handler implements HandlerInterface {

	public String[] filter;

	public Handler(Map<String, Object> args) throws Exception {

		Object object = args.get("filter");
		if (object == null) {
			throw new Exception("Missing 'output.filter' field");
		} else if (!(object instanceof ArrayList)) {
			throw new Exception("'output.filter' is not an Array");
		}

		ArrayList list = (ArrayList) object;
		filter = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Object object2 = list.get(i);

			if (!(object2 instanceof String)) {
				throw new Exception("'output.filter' contains the non-String value: " + object + ", type: " + object.getClass().getSimpleName());
			}

			filter[i] = (String) object2;
		}
	}

	@Override
	public boolean isText() {
		return false;
	}

	@Override
	public boolean isImage() {
		return false;
	}

	@Override
	public boolean isSVG() {
		return false;
	}
}
