package com.rsmaxwell.infection.model.handler;

import java.util.Map;

public abstract class HandlerGraphics extends Handler {

	protected int width;
	protected int height;

	public HandlerGraphics(Map<String, Object> args) throws Exception {

		super(args);

		Object object = args.get("width");
		if (object == null) {
			throw new Exception("Missing 'output.width' field");
		} else if (!(object instanceof Number)) {
			throw new Exception("'output.width' is not a number");
		}

		Number number = (Number) object;
		width = number.intValue();

		object = args.get("height");
		if (object == null) {
			throw new Exception("Missing 'output.height' field");
		} else if (!(object instanceof Number)) {
			throw new Exception("'output.height' is not a number");
		}

		number = (Number) object;
		height = number.intValue();
	}
}
