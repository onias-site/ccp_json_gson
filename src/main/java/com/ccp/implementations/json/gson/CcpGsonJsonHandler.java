package com.ccp.implementations.json.gson;

import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.especifications.json.CcpJsonHandler;

/**
 * DI provider that exposes {@code GsonJsonHandler} as the {@code CcpJsonHandler} implementation.
 */
public class CcpGsonJsonHandler implements CcpInstanceProvider<CcpJsonHandler>{

	public CcpJsonHandler getInstance() {
		GsonJsonHandler gsonJsonHandler = new GsonJsonHandler();
		return gsonJsonHandler;
	}
}
