package com.ccp.implementations.json.gson;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ClassExclusionStrategy implements ExclusionStrategy{

	public final static ClassExclusionStrategy INSTANCE = new ClassExclusionStrategy();

	private ClassExclusionStrategy() {}
	
	
	public boolean shouldSkipField(FieldAttributes f) {
		Type declaredType = f.getDeclaredType();
		
		return declaredType.getTypeName().startsWith(Class.class.getName());
//		return false;
	}

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
