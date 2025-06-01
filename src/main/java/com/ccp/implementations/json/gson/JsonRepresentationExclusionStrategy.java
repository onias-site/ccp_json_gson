package com.ccp.implementations.json.gson;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

class JsonRepresentationExclusionStrategy implements ExclusionStrategy{

	public final static JsonRepresentationExclusionStrategy INSTANCE = new JsonRepresentationExclusionStrategy();

	private JsonRepresentationExclusionStrategy() {}
	
	
	public boolean shouldSkipField(FieldAttributes f) {
		Type declaredType = f.getDeclaredType();
		
		String typeName = declaredType.getTypeName();
		String name = Class.class.getName();
		boolean startsWith = typeName.startsWith(name);
		return startsWith;
	}

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
