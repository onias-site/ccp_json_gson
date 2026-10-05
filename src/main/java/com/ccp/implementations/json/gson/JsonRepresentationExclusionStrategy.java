package com.ccp.implementations.json.gson;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Gson exclusion strategy that skips the fields whose declared type is {@code Class} (or a parameterization of it),
 * avoiding the serialization of class references.
 */
class JsonRepresentationExclusionStrategy implements ExclusionStrategy{

	/** The single instance. */
	public final static JsonRepresentationExclusionStrategy INSTANCE = new JsonRepresentationExclusionStrategy();

	/** Singleton; use {@link #INSTANCE}. */
	private JsonRepresentationExclusionStrategy() {}
	
	
	/**
	 * Skips the fields declared as {@code Class}.
	 * @param f the field
	 * @return {@code true} for a {@code Class} field
	 */
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
	/**
	 * Tells whether the declared type name of the field starts with the name of the given type.
	 * @param f the field
	 * @param skippedType the type to skip
	 * @return {@code true} when the field must be skipped
	 */
	@SuppressWarnings("rawtypes")
	private boolean skip(FieldAttributes f, Class<Class> skippedType) {
		Type declaredType = f.getDeclaredType();
		
		String typeName = declaredType.getTypeName();
		String name = skippedType.getName();
		boolean shouldSkip = typeName.startsWith(name);
		return shouldSkip;
	}

	/**
	 * No class is skipped as a whole.
	 * @param clazz the class
	 * @return always {@code false}
	 */
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
