package com.rsmaxwell.infection.config;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {

	public double N = 1;
	public double iStart = 0.01;

	@JsonIgnore
	public double sStart = N - iStart;

	public double rStart = 0;
	public double transmission = 3.2;
	public double recovery = 0.23;
	public double maxTime = 20;
	public int resolution = 10;

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
	}

	static public Config load(String filename) throws Exception {

		Config config = null;

		try {
			config = objectMapper.readValue(new File(filename), Config.class);

			config.sStart = config.N - config.iStart;

		} catch (Exception e) {
			throw new Exception(filename, e);
		}

		return config;
	}
}
