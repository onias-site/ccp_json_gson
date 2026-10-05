package com.ccp.implementations.json.gson;

import java.util.List;
import java.util.Map;

import com.ccp.especifications.json.CcpJsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@code CcpJsonHandler} implementation using Gson 2.7. Serialization skips fields whose declared type is {@code Class}
 * (see {@code JsonRepresentationExclusionStrategy}). Note that the shared builder is mutated on every call (the exclusion
 * strategy is added again and, after the first pretty print, pretty printing stays on in the builder).
 */
class GsonJsonHandler implements CcpJsonHandler {

	/** Builder shared by every serialization. */
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	/** Plain Gson used to parse and to normalize. */
	private static final Gson GSON = new Gson();

	
	/**
	 * Serializes the object as compact JSON: it is serialized with the exclusion strategy, parsed back into maps and lists
	 * and serialized again (so numbers become decimals, e.g. {@code 1} becomes {@code 1.0}).
	 * @param object the object to serialize
	 * @return the compact JSON text
	 */
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

	
	/**
	 * Serializes the object as indented JSON, with the exclusion strategy.
	 * @param object the object to serialize
	 * @return the indented JSON text
	 */
	public String asPrettyJson(Object object) {
		GsonBuilder prettyBuilder = GSON_BUILDER.setPrettyPrinting();
		GsonBuilder prettyBuilderWithExclusion = prettyBuilder
				.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE);
				Gson prettyGson = prettyBuilderWithExclusion
				.create();
				String prettyJson = prettyGson.toJson(object);
				return prettyJson;
	}

	/**
	 * Parses the JSON text into maps, lists, strings, doubles and booleans.
	 * @param <T> the expected type
	 * @param json the JSON text
	 * @return the parsed object
	 */
	@SuppressWarnings("unchecked")
	
	public <T> T  fromJson(String json) {
		Object parsedJson = GSON.fromJson(json, Object.class);
		T result = (T)parsedJson;
		return result;
	}

	
	/**
	 * Tells whether the text parses as a JSON object.
	 * @param src the text
	 * @return {@code true} for a JSON object
	 */
	public boolean isValidJson(String src) {
		boolean validType = this.isValidType(src, Map.class);
		return validType;
	}

	/**
	 * Tells whether the text parses as a JSON list.
	 * @param src the text
	 * @return {@code true} for a JSON list
	 */
	public boolean isValidJsonList(String src) {
		boolean validType = this.isValidType(src, List.class);
		return validType;
	}


	/**
	 * Tells whether the text parses into the given type without error and is not empty.
	 * @param src the text
	 * @param classOfT the expected type
	 * @return {@code true} when the parse succeeds with a non-null result
	 */
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
