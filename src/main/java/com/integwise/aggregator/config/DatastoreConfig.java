package com.integwise.aggregator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.exception.UnsupportedDatastoreTypeException;
import com.integwise.aggregator.store.MapDataStore;

/**
* Datastore configuration as per application configuration
* Datastoretype has to be configured in application.properties
* 
* @author Kishor Chukka
* 
*/
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class DatastoreConfig {
	
	private static final Logger LOGGER =
		      LoggerFactory.getLogger(DatastoreConfig.class);
	
	private ApplicationProperties properties;
	
	public DatastoreConfig(ApplicationProperties appProperties) {
		  this.properties = appProperties;
	}
	
	@Bean(name="priceDataStore")
	public MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore() throws UnsupportedDatastoreTypeException {
		if(properties.getDataStoreType() == null || properties.getDataStoreType().equals("MAPDB")) {
			return new MapDataStore<>();
		} else {
			throw new UnsupportedDatastoreTypeException("Unsupported Datastore Type Error "+ properties.getDataStoreType());
		}
	}
	
}
