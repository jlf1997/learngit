package com.cimr.boot.mongodb.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;

public abstract class AbstractMongoConfig {
	
	protected MongoProperties properties;

	 abstract public MongoTemplate getMongoTemplate() throws Exception;
	
	 @SuppressWarnings("deprecation")
	public MongoDbFactory simpleFactory() throws Exception {
	        return new SimpleMongoDbFactory(new MongoURI(properties.getUri()));
	    }
	 
	 
	 //暂时 不使用
	 private MongoDbFactory mongoDbFactory() throws Exception {

	        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
	        ServerAddress addr = new ServerAddress("10.100.138.95",27016);
	        ServerAddress addr2 = new ServerAddress("10.100.138.95", 27017);
	        ServerAddress addr3 = new ServerAddress("10.100.138.96", 27018);
	        addresses.add(addr);
	        addresses.add(addr2);
	        addresses.add(addr3);
	        MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(
	                "mongoProperties.getUsername()",
	                "mongoProperties.getDatabase()",
	                "mongoProperties.getPassword()".toCharArray());
	        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
	        credentialsList.add(mongoCredential);
	        MongoDbFactory mongoDbFactory =   new SimpleMongoDbFactory(new MongoClient(addresses, credentialsList),
	                "mongoProperties.getDatabase()");
	        return mongoDbFactory;
	    }

	public MongoProperties getProperties() {
		return properties;
	}

	public void setProperties(MongoProperties properties) {
		this.properties = properties;
	}
	 
	 
}
