package com.integwise.aggregator.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
* CacheEventLogger can be used to log custom log messages for Ehcache events
* 
* @author Kishor Chukka
* 
*/
public class CacheEventLogger 
  implements CacheEventListener<Object, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventLogger.class);
	
    @Override
    public void onEvent(
      CacheEvent<? extends Object, ? extends Object> cacheEvent) {
    	LOGGER.info("Cache event {} for item with key {}. Old value = {}, New value = {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}