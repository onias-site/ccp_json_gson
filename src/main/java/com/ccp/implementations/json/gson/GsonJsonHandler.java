package com.ccp.implementations.json.gson;

import java.util.List;
import java.util.Map;

import com.ccp.especifications.json.CcpJsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@code CcpJsonHandler} implementation using Gson 2.7. Applies
 * {@code JsonRepresentationExclusionStrategy} to skip fields of type {@code Class} and
 * {@code CcpBusiness} during serialization.
 */
class GsonJsonHandler implements CcpJsonHandler {

	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final Gson GSON = new Gson();

	
	public String toJson(Object object) {
		GsonBuilder builderWithExclusion = GSON_BUILDER
				.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE);
				Gson gson = builderWithExclusion
				.create();
				String json = gson.toJson(object);
		Object parsedJson = this.fromJson(json);
		String normalizedJson = GSON.toJson(parsedJson);
		return normalizedJson;
	}

	
	public String asPrettyJson(Object object) {
		GsonBuilder prettyBuilder = GSON_BUILDER.setPrettyPrinting();
		GsonBuilder prettyBuilderWithExclusion = prettyBuilder
				.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE);
				Gson prettyGson = prettyBuilderWithExclusion
				.create();
				String prettyJson = prettyGson.toJson(object);
				return prettyJson;
	}

	@SuppressWarnings("unchecked")
	
	public <T> T  fromJson(String json) {
		Object parsedJson = GSON.fromJson(json, Object.class);
		T result = (T)parsedJson;
		return result;
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
			var parsedJson = GSON.fromJson(src, classOfT);
			boolean isValid = parsedJson != null;
			return isValid;
		} catch (Exception e) {
			return false;
		}
	}
}
