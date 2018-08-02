package com.cimr.fallback;

import org.springframework.stereotype.Component;

@Component
public class ClientFallBack extends DefaultProducerFallback{

	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return "client";
	}

}
