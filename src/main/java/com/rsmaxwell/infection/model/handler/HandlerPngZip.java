package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerPngZip extends HandlerGraphics {

	public HandlerPngZip(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(ResponseCollectorInterface response, Populations populations) throws Exception {

		response.setTypeZip();

		OutputStream out = response.getOutputStream();
		populations.output_pngzip(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.png.zip";
	}
}
