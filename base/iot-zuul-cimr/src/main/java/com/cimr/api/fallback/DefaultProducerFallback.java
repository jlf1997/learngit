package com.cimr.api.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.GsonUtil;
import com.cimr.boot.utils.LogsUtil;

public abstract class DefaultProducerFallback implements FallbackProvider{

	
	private static final Logger log = LoggerFactory.getLogger(DefaultProducerFallback.class);

	
	public ClientHttpResponse fallbackResponse() {
		// TODO Auto-generated method stub
		return fallbackResponse(null,null);
	}

	public ClientHttpResponse fallbackResponse(Throwable cause) {
		// TODO Auto-generated method stub
		return fallbackResponse(null,cause);
	}


	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
		            @Override
		            public HttpStatus getStatusCode() throws IOException {
		                return HttpStatus.OK;
		            }

		            @Override
		            public int getRawStatusCode() throws IOException {
		                return 200;
		            }

		            @Override
		            public String getStatusText() throws IOException {
		                return "OK";
		            }

		            @Override
		            public void close() {

		            }

		            @Override
		            public InputStream getBody() throws IOException {
		            	String causemessage = LogsUtil.getStackTrace(cause);
		    			log.error(causemessage);
		            	HttpResult res = new HttpResult(false,"The service is unavailable.");
		                return new ByteArrayInputStream(GsonUtil.objToJson(res).getBytes());
		            }

		            @Override
		            public HttpHeaders getHeaders() {
		                HttpHeaders headers = new HttpHeaders();
		                headers.setContentType(MediaType.APPLICATION_JSON);
		                return headers;
		            }
		        };
	}

	

	
}
