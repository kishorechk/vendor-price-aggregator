package com.integwise.aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = 	"app.aggregator", ignoreUnknownFields = false)
public class ApplicationProperties {
	private String consumerJmsTopic;
	private String dataStoreType;
	private int cacheTimeToLiveDays;
	private long cacheMaxEntries;

	public String getConsumerJmsTopic() {
		return consumerJmsTopic;
	}
	
	public String getDataStoreType() {
		return dataStoreType;
	}
	
	public int getCacheTimeToLiveDays() {
		return cacheTimeToLiveDays;
	}
	
	public long getCacheMaxEntries() {
		return cacheMaxEntries;
	}

	public ApplicationProperties setConsumerJmsTopic(String consumerJmsTopic) {
		this.consumerJmsTopic = consumerJmsTopic;
		return this;
	}
	
	public ApplicationProperties setDataStoreType(String dataStoreType) {
		this.dataStoreType = dataStoreType;
		return this;
	}
	
	public ApplicationProperties setCacheTimeToLiveDays(int cacheTimeToLiveDays) {
		this.cacheTimeToLiveDays = cacheTimeToLiveDays;
		return this;
	}
	
	public ApplicationProperties setCacheMaxEntries(long cacheMaxEntries) {
		this.cacheMaxEntries = cacheMaxEntries;
		return this;
	}
}
