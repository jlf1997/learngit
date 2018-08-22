package com.cimr.boot.mongodb.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoURI;

public abstract class AbstractMongoConfig {
	
	protected MongoProperties properties;

	 abstract public MongoTemplate getMongoTemplate() throws Exception;
	
	 @SuppressWarnings("deprecation")
	public MongoDbFactory simpleFactory() throws Exception {
	        return new SimpleMongoDbFactory(new MongoURI(properties.getUri()));
	    }
	 
	 
	

	public MongoProperties getProperties() {
		return properties;
	}

	public void setProperties(MongoProperties properties) {
		this.properties = properties;
	}
	 
	 
}
