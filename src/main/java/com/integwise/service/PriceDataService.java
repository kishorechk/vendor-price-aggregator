package com.integwise.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.integwise.domain.InstrumentPrice;
import com.integwise.repository.PriceDataRepository;

@Service
public class PriceDataService {
	
	Logger logger = LoggerFactory.getLogger(PriceDataService.class);
	
	@Autowired
    PriceDataRepository priceDataRepository;
    
    @Cacheable(value="vendor-all-instrument-prices", key = "#vendorId")
    public List<InstrumentPrice> getPricesByVendorId(String vendorId) throws ParseException {
    	logger.info("getPricesByVendorId");
        return priceDataRepository.getPricesByVendorId(vendorId);
    }

    @Cacheable(value="instrument-all-vendor-prices", key = "#instrumentId")
    public List<InstrumentPrice> getPricesByInstrumentId(String instrumentId) throws ParseException {
    	logger.info("getPricesByInstrumentId");
        return priceDataRepository.getPricesByInstrumentId(instrumentId);
    }

    public void addOrUpdate(InstrumentPrice price) {
        priceDataRepository.addOrUpdate(price);
    }
}