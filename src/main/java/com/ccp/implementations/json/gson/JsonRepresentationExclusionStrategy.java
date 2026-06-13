package com.ccp.implementations.json.gson;

import java.lang.reflect.Type;

import com.ccp.business.CcpBusiness;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Estratégia de exclusão do Gson que ignora campos cujo tipo declarado seja {@code Class} ou
 * {@code CcpBusiness}, evitando serialização recursiva ou de referências funcionais.
 */
class JsonRepresentationExclusionStrategy implements ExclusionStrategy{

	public final static JsonRepresentationExclusionStrategy INSTANCE = new JsonRepresentationExclusionStrategy();

	private JsonRepresentationExclusionStrategy() {}
	
	
	public boolean shouldSkipField(FieldAttributes f) {
		boolean startsWith = this.skip(f, Class.class, CcpBusiness.class);
		return startsWith;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean skip(FieldAttributes f, Class... class1) {
		for (Class class2 : class1) {
			boolean skip = this.skip(f, class2);
			if(skip) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private boolean skip(FieldAttributes f, Class<Class> class1) {
		Type declaredType = f.getDeclaredType();
		
		String typeName = declaredType.getTypeName();
		String name = class1.getName();
		boolean startsWith = typeName.startsWith(name);
		return startsWith;
	}

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
