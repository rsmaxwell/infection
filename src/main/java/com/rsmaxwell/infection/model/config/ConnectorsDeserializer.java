package com.rsmaxwell.infection.model.config;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

class ConnectorsDeserializer implements JsonDeserializer<Connectors> {

	@Override
	public Connectors deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Iterator<Map.Entry<String, JsonElement>> ite = json.getAsJsonObject().entrySet().iterator();

		Connectors connectors = new Connectors();

		while (ite.hasNext()) {
			Map.Entry<String, JsonElement> entry = ite.next();

			Pair key = null;
			try {
				key = Connector.validateName(entry.getKey());
			} catch (Exception e) {
				throw new JsonParseException(e);
			}

			Connector connectorInfo = context.deserialize(entry.getValue(), Connector.class);
			connectors.put(key, connectorInfo);
		}

		return connectors;
	}
}
