package com.rsmaxwell.infection.model.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface ResponseCollectorInterface {

	OutputStream getOutputStream() throws IOException;

	PrintWriter getWriter() throws IOException;

	void setTypeJpeg();

	void setTypePng();

	void setTypeZip();

	void setTypeJson();

	void setTypeText();

	void setTypeSvg();
}
