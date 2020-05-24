package com.rsmaxwell.infection.model.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.rsmaxwell.infection.model.handler.Handler;
import com.rsmaxwell.infection.model.handler.HandlerInterface;
import com.rsmaxwell.infection.model.handler.HandlerJpeg;
import com.rsmaxwell.infection.model.handler.HandlerJpegZip;
import com.rsmaxwell.infection.model.handler.HandlerJson;
import com.rsmaxwell.infection.model.handler.HandlerPng;
import com.rsmaxwell.infection.model.handler.HandlerPngZip;
import com.rsmaxwell.infection.model.handler.HandlerSvg;
import com.rsmaxwell.infection.model.handler.HandlerSvgZip;
import com.rsmaxwell.infection.model.handler.HandlerText;
import com.rsmaxwell.infection.model.integrate.Euler;

public class Config {

	public double maxTime;
	public Integer resolution;
	public String integrationMethod;
	public Groups groups = new Groups();
	public Connectors connectors = new Connectors();
	public Map<String, Object> output = new HashMap<String, Object>();
	public Handler handler;

	public static Config INSTANCE;

	private static Map<String, Class> handlers = new HashMap<String, Class>();

	static {
		handlers.put("png", HandlerPng.class);
		handlers.put("text", HandlerText.class);
		handlers.put("svg", HandlerSvg.class);
		handlers.put("json", HandlerJson.class);
		handlers.put("jpeg", HandlerJpeg.class);
		handlers.put("pngzip", HandlerPngZip.class);
		handlers.put("jpegzip", HandlerJpegZip.class);
		handlers.put("svgzip", HandlerSvgZip.class);
	}

	public static Config loadFromFile(String filename) throws Exception {
		String content = new String(Files.readAllBytes(Paths.get(filename)));
		return load(content);
	}

	public static Config load(String json) throws Exception {
		Config config = null;
		Gson gson = new GsonBuilder().registerTypeAdapter(Connectors.class, new ConnectorsDeserializer()).create();

		try {
			config = gson.fromJson(json, Config.class);
		} catch (JsonSyntaxException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.printf(e.getMessage());
			pw.println();
			String lines[] = json.split("\\r?\\n");
			for (int i = 0; i < lines.length; i++) {
				pw.printf("%3d:   %s\n", i + 1, lines[i]);
			}
			throw new ConfigSyntaxException(sw.getBuffer().toString(), e);
		}

		// ******************************************************************
		// * Set defaults where necessary
		// ******************************************************************
		if (config.integrationMethod == null) {
			config.integrationMethod = Euler.class.getName();
		}

		if (config.resolution == null) {
			config.resolution = 10;
		}

		// ******************************************************************
		// * Get the appropriate handler for the requested 'output.format'
		// ******************************************************************
		Object object = config.output.get("format");
		if (object == null) {
			throw new Exception("Missing 'output.format' field");
		} else if (!(object instanceof String)) {
			throw new Exception("'output.format' is not a String");
		}

		String format = (String) object;
		Class<? extends HandlerInterface> handlerClass = handlers.get(format);
		if (handlerClass == null) {
			throw new Exception("Unexpected value of 'output.format': " + format);
		}

		Class<?>[] parameterTypeArray = { Map.class };
		Constructor<?> ctor = handlerClass.getConstructor(parameterTypeArray);
		if (ctor == null) {
			throw new Exception("The handler '" + handlerClass.getSimpleName() + "' does not have a suitable constructor");
		}

		Object[] parameters = { config.output };
		config.handler = (Handler) ctor.newInstance(parameters);

		// ******************************************************************
		// * Update and validate the instance
		// ******************************************************************
		config.setInstance();

		return config.validate();
	}

	public void setInstance() throws Exception {
		INSTANCE = this;
	}

	// ******************************************************************
	// * Check the connectors match the groups
	// ******************************************************************
	public Config validate() throws Exception {

		Connector defaultConnector = connectors.get("default");
		if (defaultConnector == null) {
			for (String id1 : groups.keySet()) {
				for (String id2 : groups.keySet()) {
					String key = id1 + "." + id2;
					Connector connector = connectors.get(key);
					if (connector == null) {
						throw new Exception("Missing connector: key: " + key);
					}
				}
			}
		}

		for (String key : connectors.keySet()) {

			ConnectorNameInfo info = new ConnectorNameInfo(key);

			if (info.isSpecial()) {
				continue;
			}

			for (int index = 0; index < 2; index++) {
				String id = info.getName(index);
				Group group = groups.get(id);
				if (group == null) {
					throw new Exception("Extraneous connector: key: " + key + ", (group " + id + " not found");
				}
			}
		}

		if (groups.size() == 0) {
			throw new Exception("There are no groups");
		}

		return this;
	}
}
