package com.rsmaxwell.infection.model.integrate;

import com.rsmaxwell.infection.model.engine.Population;
import com.rsmaxwell.infection.model.model.Quantity;

public interface Rate {

	Quantity rate(double t, double h, Population population, Population other, double recovery, double transmission);

}
