package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerPng extends HandlerGraphics {

	public HandlerPng(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(ResponseCollectorInterface response, Populations populations) throws Exception {

		response.setTypePng();

		OutputStream out = response.getOutputStream();
		populations.output_png(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.png";
	}

	@Override
	public boolean isImage() {
		return true;
	}
}
