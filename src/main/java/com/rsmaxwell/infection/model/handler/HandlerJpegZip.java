package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerJpegZip extends HandlerGraphics {

	public HandlerJpegZip(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(MyResponseInterface response, Populations populations) throws Exception {

		response.setTypeZip();

		OutputStream out = response.getOutputStream();
		populations.output_jpegzip(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.jpeg.zip";
	}
}
