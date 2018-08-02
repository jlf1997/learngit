package com.cimr.fallback;

import org.springframework.stereotype.Component;

@Component
public class ProducerFallback extends DefaultProducerFallback{

	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return "shop-bs";
	}

	

}
