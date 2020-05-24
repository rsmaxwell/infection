package com.rsmaxwell.infection.model.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface MyResponseInterface {

	OutputStream getOutputStream() throws IOException;

	PrintWriter getWriter() throws IOException;
}
