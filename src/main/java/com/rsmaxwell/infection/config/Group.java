package com.rsmaxwell.infection.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Group {

	public String name;
	public double recovery;
	public double population;
	public double iStart;
	public double rStart;

	@JsonIgnore
	public double N;
}
