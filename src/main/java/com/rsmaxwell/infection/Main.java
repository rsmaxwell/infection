package com.rsmaxwell.infection;

import com.rsmaxwell.infection.config.Config;
import com.rsmaxwell.infection.model.Model;

public class Main {

	public static void main(String[] args) {

		Config config = new Config();

		Model model = new Model(config);
		model.run();
	}

}
