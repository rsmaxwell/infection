package com.rsmaxwell.infection.model.handler;

import java.io.OutputStream;
import java.util.Map;

import com.rsmaxwell.infection.model.engine.Populations;

public class HandlerSvg extends HandlerGraphics {

	public HandlerSvg(Map<String, Object> args) throws Exception {
		super(args);
	}

	@Override
	public void format(ResponseCollectorInterface response, Populations populations) throws Exception {

		response.setTypeSvg();

		OutputStream out = response.getOutputStream();
		populations.output_svg(out, filter, width, height);
	}

	@Override
	public String getFilename() {
		return "result.svg";
	}

	@Override
	public boolean isSVG() {
		return true;
	}
}
