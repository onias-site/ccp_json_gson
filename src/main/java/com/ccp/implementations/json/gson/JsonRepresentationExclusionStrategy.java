package com.ccp.implementations.json.gson;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Gson exclusion strategy that skips fields whose declared type is {@code Class} or
 * {@code CcpBusiness}, avoiding recursive serialization or serialization of functional references.
 */
class JsonRepresentationExclusionStrategy implements ExclusionStrategy{

	public final static JsonRepresentationExclusionStrategy INSTANCE = new JsonRepresentationExclusionStrategy();

	private JsonRepresentationExclusionStrategy() {}
	
	
	public boolean shouldSkipField(FieldAttributes f) {
		boolean shouldSkip = this.skip(f, Class.class);
		return shouldSkip;
	}
/*
 * 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean skip(FieldAttributes f, Class... skippedType) {
		for (Class class2 : skippedType) {
			boolean skip = this.skip(f, class2);
			if(skip) {
				return true;
			}
		}
		return false;
	}
 */
	@SuppressWarnings("rawtypes")
	private boolean skip(FieldAttributes f, Class<Class> skippedType) {
		Type declaredType = f.getDeclaredType();
		
		String typeName = declaredType.getTypeName();
		String name = skippedType.getName();
		boolean shouldSkip = typeName.startsWith(name);
		return shouldSkip;
	}

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
