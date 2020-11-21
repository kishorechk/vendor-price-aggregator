package com.integwise.aggregator.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integwise.aggregator.Constants;
import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.jms.PriceDataPublisher;
import com.integwise.aggregator.store.MapDataStore;

@Service
@Transactional
public class PriceDataService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PriceDataService.class);
	
	
	private final PriceDataPublisher priceDataPublisher;
	
    private final MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore;
	
	private final CacheManager cacheManager;
	
	public PriceDataService(MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore,
			CacheManager cacheManager, PriceDataPublisher priceDataPublisher) {
		this.priceDataStore = priceDataStore;
		this.cacheManager = cacheManager;
		this.priceDataPublisher = priceDataPublisher;
	}
    
    @Cacheable(cacheNames=Constants.GET_PRICES_BY_VENDOR)
    public Set<InstrumentPrice> getPricesByVendorId(String vendorId) {
    	LOGGER.info("Get Prices By Vendor Id");
        return new HashSet<> (priceDataStore.getAll().stream().filter(v -> v.getKey().getVendorId().equals(vendorId)).collect(Collectors.toList()));
    }

    @Cacheable(cacheNames=Constants.GET_PRICES_BY_INSTRUMENT)
    public Set<InstrumentPrice> getPricesByInstrumentId(String instrumentId) {
    	LOGGER.info("Get Prices By Instrument Id");
        return new HashSet<> (priceDataStore.getAll().stream().filter(v -> v.getKey().getInstrumentId().equals(instrumentId)).collect(Collectors.toList()));
    }

    public void addOrUpdate(InstrumentPrice price) {
        LOGGER.info("Add or Update price");
        
        updateVendorCache(price);
        updateInstrumentCache(price);
        priceDataStore.addOrUpdate(price.getKey(), price);
        
        publish(price);
    }

	private void updateInstrumentCache(InstrumentPrice price) {
		Cache cache = cacheManager.getCache(Constants.GET_PRICES_BY_INSTRUMENT);
		Cache.ValueWrapper instrumentCache = cache.get(price.getInstrumentId());
		HashSet<InstrumentPrice> set;
		if(instrumentCache!=null) {
			set = ((HashSet)instrumentCache.get());
			set.add(price);
		} else {
			set = new HashSet(Arrays.asList(price));
		}
		cacheManager.getCache(Constants.GET_PRICES_BY_INSTRUMENT).put(price.getInstrumentId(), set);
	}

	private void updateVendorCache(InstrumentPrice price) {
		Cache cache = cacheManager.getCache(Constants.GET_PRICES_BY_VENDOR);
		Cache.ValueWrapper instrumentCache = cache.get(price.getVendorId());
		HashSet<InstrumentPrice> set;
		if(instrumentCache!=null) {
			set = ((HashSet)instrumentCache.get());
			set.add(price);
		} else {
			set = new HashSet(Arrays.asList(price));
		}
		cacheManager.getCache(Constants.GET_PRICES_BY_VENDOR).put(price.getVendorId(), set);
	}
	
	public void publish(InstrumentPrice price) {
		priceDataPublisher.publish(price);
	}
}