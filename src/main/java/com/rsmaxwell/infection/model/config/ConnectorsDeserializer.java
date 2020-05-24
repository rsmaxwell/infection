package com.rsmaxwell.infection.model.config;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rsmaxwell.infection.model.expression.Evaluate;
import com.rsmaxwell.infection.model.expression.Expression;
import com.rsmaxwell.infection.model.expression.Script;
import com.rsmaxwell.infection.model.expression.Value;

class ConnectorsDeserializer implements JsonDeserializer<Connectors> {

	@Override
	public Connectors deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Iterator<Map.Entry<String, JsonElement>> ite = json.getAsJsonObject().entrySet().iterator();

		Connectors connectors = new Connectors();

		while (ite.hasNext()) {
			Map.Entry<String, JsonElement> entry = ite.next();
			String name = entry.getKey();

			try {
				new ConnectorNameInfo(name);
			} catch (Exception e) {
				throw new JsonParseException(e);
			}

			ConnectorLiteral literal = context.deserialize(entry.getValue(), ConnectorLiteral.class);
			Connector connector = new Connector();

			Expression expression = null;
			if (literal.transmission != null) {
				expression = new Value(literal.transmission);
			} else if (literal.transmissionEval != null) {
				expression = new Evaluate(literal.transmissionEval);
			} else if (literal.transmissionScript != null) {
				expression = new Script(literal.transmissionScript);
			} else {
				expression = new Value(0);
			}
			connector.setTransmission(expression);
			connectors.put(name, connector);
		}

		return connectors;
	}
}
