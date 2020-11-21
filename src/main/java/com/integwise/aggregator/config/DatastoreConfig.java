package com.integwise.aggregator.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.exception.UnsupportedDatastoreTypeException;
import com.integwise.aggregator.store.MapDataStore;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class DatastoreConfig {
	
	private ApplicationProperties properties;
	
	public DatastoreConfig(ApplicationProperties appProperties) {
		  this.properties = appProperties;
	}
	
	@Bean(name="priceDataStore")
	public MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore() throws UnsupportedDatastoreTypeException {
		if(properties.getDataStoreType() == null || properties.getDataStoreType().equals("MAP")) {
			return new MapDataStore<>();
		} else {
			throw new UnsupportedDatastoreTypeException("Unsupported Datastore Type Error "+ properties.getDataStoreType());
		}
	}
	
}
