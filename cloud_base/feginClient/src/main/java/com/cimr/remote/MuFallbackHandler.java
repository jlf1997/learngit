package com.cimr.remote;

import java.util.Map;

import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.ribbon.hystrix.FallbackHandler;

import rx.Observable;

public class MuFallbackHandler implements FallbackHandler{

	@Override
	public Observable getFallback(HystrixInvokableInfo hystrixInfo, Map requestProperties) {
		// TODO Auto-generated method stub
		return null;
	}

}
