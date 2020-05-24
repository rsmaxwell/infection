package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerJpeg extends HandlerGraphics {

	public HandlerJpeg(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(MyResponseInterface response, Populations populations) throws Exception {
		OutputStream out = response.getOutputStream();
		populations.output_jpeg(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.jpeg";
	}

	@Override
	public boolean isImage() {
		return true;
	}
}
