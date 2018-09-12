package com.cimr.boot.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

public abstract class AbstractMongoConfig {
	
	abstract public MongoTemplate getMongoTemplate() throws Exception;

	
	protected MongoProperties properties;
	
	@Autowired
	private MongoOptionProperties mongoOptionProperties;

	
    public MongoClientOptions.Builder mongoClientOptions() {
        if (mongoOptionProperties == null) {
            return new MongoClientOptions.Builder();
        }

        return new MongoClientOptions.Builder()
                .minConnectionsPerHost(mongoOptionProperties.getMinConnectionPerHost())
                .connectionsPerHost(mongoOptionProperties.getMaxConnectionPerHost())
                .threadsAllowedToBlockForConnectionMultiplier(mongoOptionProperties.getThreadsAllowedToBlockForConnectionMultiplier())
                .serverSelectionTimeout(mongoOptionProperties.getServerSelectionTimeout())
                .maxWaitTime(mongoOptionProperties.getMaxWaitTime())
                .maxConnectionIdleTime(mongoOptionProperties.getMaxConnectionIdleTime())
                .maxConnectionLifeTime(mongoOptionProperties.getMaxConnectionLifeTime())
                .connectTimeout(mongoOptionProperties.getConnectTimeout())
                .socketTimeout(mongoOptionProperties.getSocketTimeout())
                .socketKeepAlive(mongoOptionProperties.getSocketKeepAlive())
                .sslEnabled(mongoOptionProperties.getSslEnabled())
                .sslInvalidHostNameAllowed(mongoOptionProperties.getSslInvalidHostNameAllowed())
                .alwaysUseMBeans(mongoOptionProperties.getAlwaysUseMBeans())
                .heartbeatFrequency(mongoOptionProperties.getHeartbeatFrequency())
                .minConnectionsPerHost(mongoOptionProperties.getMinConnectionPerHost())
                .heartbeatConnectTimeout(mongoOptionProperties.getHeartbeatConnectTimeout())
                .heartbeatSocketTimeout(mongoOptionProperties.getSocketTimeout())
                .localThreshold(mongoOptionProperties.getLocalThreshold())
                ;
    }	
	
	 @SuppressWarnings("deprecation")
	public MongoDbFactory simpleFactory() throws Exception {
		 MongoClientURI mongoClientURI = new MongoClientURI(properties.getUri(),mongoClientOptions());
		
		 MongoDbFactory mongoDbFactory =
				 new SimpleMongoDbFactory(mongoClientURI);
	        return mongoDbFactory;
	    }
	 
	 
	

	public MongoProperties getProperties() {
		return properties;
	}

	public void setProperties(MongoProperties properties) {
		this.properties = properties;
	}
	 
	 
}
