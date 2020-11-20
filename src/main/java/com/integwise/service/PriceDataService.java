package com.integwise.service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.integwise.domain.InstrumentPrice;
import com.integwise.store.MapDataStore;

@Service
public class PriceDataService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PriceDataService.class);
	
	@Autowired
    private final MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore;
	
	public PriceDataService(MapDataStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceDataStore) {
		this.priceDataStore = priceDataStore;
	}
    
    @Cacheable(value="vendor-all-instrument-prices", key = "#vendorId")
    public List<InstrumentPrice> getPricesByVendorId(String vendorId) throws ParseException {
    	LOGGER.info("Get Prices By Vendor Id");
        return priceDataStore.getAll().stream().filter(v -> v.getKey().getVendorId().equals(vendorId)).collect(Collectors.toList());
    }

    @Cacheable(value="instrument-all-vendor-prices", key = "#instrumentId")
    public List<InstrumentPrice> getPricesByInstrumentId(String instrumentId) throws ParseException {
    	LOGGER.info("Get Prices By Instrument Id");
        return priceDataStore.getAll().stream().filter(v -> v.getKey().getInstrumentId().equals(instrumentId)).collect(Collectors.toList());
    }

    public void addOrUpdate(InstrumentPrice price) {
        LOGGER.info("Add or Update price");
        priceDataStore.addOrUpdate(price.getKey(), price);
    }
}