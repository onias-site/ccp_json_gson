package com.ccp.implementations.json.gson;

import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.especifications.json.CcpJsonHandler;

public class CcpGsonJsonHandler implements CcpInstanceProvider<CcpJsonHandler>{

	public CcpJsonHandler getInstance() {
		return new GsonJsonHandler();
	}
}
