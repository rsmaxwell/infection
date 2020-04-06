package com.rsmaxwell.corvid19.config;

import com.rsmaxwell.corvid19.config.config.Config;
import com.rsmaxwell.corvid19.config.model.Model;

public class Main {

	public static void main(String[] args) {

		Config config = new Config();

		Model model = new Model(config);
		model.run();
	}

}
