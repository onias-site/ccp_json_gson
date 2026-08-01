package com.ccp.implementations.json.gson;

import java.util.List;
import java.util.Map;

import com.ccp.especifications.json.CcpJsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Implementação de {@code CcpJsonHandler} usando o Gson 2.7. Aplica
 * {@code JsonRepresentationExclusionStrategy} para ignorar campos do tipo {@code Class} e
 * {@code CcpBusiness} na serialização.
 */
class GsonJsonHandler implements CcpJsonHandler {

	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final Gson GSON = new Gson();

	
	public String toJson(Object md) {
		String json = GSON_BUILDER
				.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE)
				.create().toJson(md);
		Object fromJson = this.fromJson(json);
		String json2 = GSON.toJson(fromJson);
		return json2;
	}

	
	public String asPrettyJson(Object md) {
		return GSON_BUILDER.setPrettyPrinting()
				.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE)
				.create().toJson(md);
	}

	@SuppressWarnings("unchecked")
	
	public <T> T  fromJson(String str) {
		Object fromJson = GSON.fromJson(str, Object.class);
		return (T)fromJson;
	}

	
	public boolean isValidJson(String src) {
		boolean validType = this.isValidType(src, Map.class);
		return validType;
	}

	public boolean isValidJsonList(String src) {
		boolean validType = this.isValidType(src, List.class);
		return validType;
	}


	protected boolean isValidType(String src, Class<?> classOfT) {
		try {
			var fromJson = GSON.fromJson(src, classOfT);
			return fromJson != null;
		} catch (Exception e) {
			return false;
		}
	}
}
