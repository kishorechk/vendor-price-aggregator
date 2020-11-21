package com.integwise.aggregator.config;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Duration;
import java.util.HashSet;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.integwise.aggregator.Constants;
import com.integwise.aggregator.service.PriceDataService;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = {DatastoreConfig.class})
@EnableConfigurationProperties(ApplicationProperties.class)
public class CacheConfig {
	
	private final javax.cache.configuration.Configuration<Object, HashSet> cacheConfiguration;
	
	public CacheConfig(ApplicationProperties properties) {
		cacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, HashSet.class,
                        ResourcePoolsBuilder.heap(properties.getCacheMaxEntries()))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.of(properties.getCacheTimeToLiveDays(), DAYS)))
                        .build());

	}
	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
        	cm.createCache(Constants.GET_PRICES_BY_VENDOR, cacheConfiguration);
            cm.createCache(Constants.GET_PRICES_BY_INSTRUMENT, cacheConfiguration);
        };
    }
}

