package com.rsmaxwell.infection.model.handler;

import java.io.PrintWriter;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerText extends Handler {

	public HandlerText(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(ResponseCollectorInterface response, Populations populations) throws Exception {

		response.setTypeText();

		try (PrintWriter out = response.getWriter()) {
			populations.print(out, filter);
		}
	}

	@Override
	public boolean isText() {
		return true;
	}

	@Override
	public String getFilename() {
		return "result.txt";
	}
}
