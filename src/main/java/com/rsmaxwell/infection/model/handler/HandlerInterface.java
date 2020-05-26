package com.rsmaxwell.infection.model.handler;

import com.rsmaxwell.infection.model.engine.Populations;

public interface HandlerInterface {

	void format(ResponseCollectorInterface response, Populations populations) throws Exception;

	boolean isText();

	boolean isImage();

	boolean isSVG();

	String getFilename();
}
