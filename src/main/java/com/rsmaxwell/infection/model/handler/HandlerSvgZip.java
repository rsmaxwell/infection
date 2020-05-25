package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerSvgZip extends HandlerGraphics {

	public HandlerSvgZip(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(MyResponseInterface response, Populations populations) throws Exception {

		response.setTypeZip();

		OutputStream out = response.getOutputStream();
		populations.output_svgzip(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.svg.zip";
	}
}
