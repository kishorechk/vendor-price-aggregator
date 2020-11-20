package com.integwise.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger 
  implements CacheEventListener<Object, Object> {
	
	Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);
	
    @Override
    public void onEvent(
      CacheEvent<? extends Object, ? extends Object> cacheEvent) {
    	logger.info("Cache event {} for item with key {}. Old value = {}, New value = {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}