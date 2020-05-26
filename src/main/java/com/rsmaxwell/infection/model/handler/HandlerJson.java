package com.rsmaxwell.infection.model.handler;

import java.io.PrintWriter;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerJson extends Handler {

	public HandlerJson(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(ResponseCollectorInterface response, Populations populations) throws Exception {

		response.setTypeJson();

		PrintWriter out = response.getWriter();
		populations.toJson(out, filter);
	}

	@Override
	public String getFilename() {
		return "result.json";
	}

	@Override
	public boolean isText() {
		return true;
	}
}
