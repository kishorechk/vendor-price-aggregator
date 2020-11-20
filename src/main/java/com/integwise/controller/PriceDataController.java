package com.integwise.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integwise.domain.InstrumentPrice;
import com.integwise.service.PriceDataService;

@RestController
@RequestMapping("/api/prices")
public class PriceDataController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PriceDataController.class);
	
    private PriceDataService priceDataService;
    
    public PriceDataController(PriceDataService priceDataService) {
    	this.priceDataService = priceDataService;
    }

    @GetMapping("/vendor/{vendorId}")
    List<InstrumentPrice> getPricesByVendorId(@PathVariable String vendorId) throws ParseException {
    	LOGGER.info("call getPricesByVendorId {}", vendorId);
        return priceDataService.getPricesByVendorId(vendorId);
    }

    @GetMapping("/instrument/{instrumentId}")
    List<InstrumentPrice> getPricesByInstrumentId(@PathVariable String instrumentId) throws ParseException {
    	LOGGER.info("getPricesByInstrumentId {}", instrumentId);
        return priceDataService.getPricesByInstrumentId(instrumentId);
    }
}