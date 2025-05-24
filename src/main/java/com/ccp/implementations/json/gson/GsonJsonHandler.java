package com.ccp.implementations.json.gson;

import java.util.Map;

import com.ccp.especifications.json.CcpJsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GsonJsonHandler implements CcpJsonHandler {

	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private static final Gson GSON = new Gson();

	
	public String toJson(Object md) {
		String json = GSON_BUILDER
				.setExclusionStrategies(ClassExclusionStrategy.INSTANCE)
				.create().toJson(md);
		Map<String, Object> fromJson = this.fromJson(json);
		String json2 = GSON.toJson(fromJson);
		return json2;
	}

	
	public String asPrettyJson(Object md) {
		return GSON_BUILDER.setPrettyPrinting()
				.setExclusionStrategies(ClassExclusionStrategy.INSTANCE)
				.create().toJson(md);
	}

	@SuppressWarnings("unchecked")
	
	public <T> T  fromJson(String str) {
		Object fromJson = GSON.fromJson(str, Object.class);
		return (T)fromJson;
	}

	
	public boolean isValidJson(String src) {
		try {
			GSON.fromJson(src, Map.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	

}
