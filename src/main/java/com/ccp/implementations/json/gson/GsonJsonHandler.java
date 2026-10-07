package com.ccp.implementations.json.gson;

import java.util.List;
import java.util.Map;

import com.ccp.especifications.json.CcpJsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@code CcpJsonHandler} implementation using Gson 2.7. Serialization skips fields whose declared type is {@code Class}
 * (see {@code JsonRepresentationExclusionStrategy}).
 * <p>
 * The serializers are immutable and built once. Until 2026-10-07 every call mutated a shared static {@code GsonBuilder}:
 * {@code setExclusionStrategies} appends, so the same strategy was added once more per serialization (an unbounded list
 * that every field of every later serialization went through), a new {@code Gson} was built per call, pretty printing
 * stayed on in the builder after the first pretty print, and concurrent requests mutated the same builder.
 */
class GsonJsonHandler implements CcpJsonHandler {

	/** Compact serializer with the exclusion strategy. */
	static final Gson GSON_WITH_EXCLUSION = new GsonBuilder()
			.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE)
			.create();
	/** Indented serializer with the exclusion strategy. */
	static final Gson PRETTY_GSON_WITH_EXCLUSION = new GsonBuilder()
			.setPrettyPrinting()
			.setExclusionStrategies(JsonRepresentationExclusionStrategy.INSTANCE)
			.create();
	/** Plain Gson used to parse and to normalize. */
	private static final Gson GSON = new Gson();


	/**
	 * Serializes the object as compact JSON: it is serialized with the exclusion strategy, parsed back into maps and lists
	 * and serialized again (so numbers become decimals, e.g. {@code 1} becomes {@code 1.0}).
	 * @param object the object to serialize
	 * @return the compact JSON text
	 */
	public String toJson(Object object) {
		String json = GSON_WITH_EXCLUSION.toJson(object);
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
		String prettyJson = PRETTY_GSON_WITH_EXCLUSION.toJson(object);
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
