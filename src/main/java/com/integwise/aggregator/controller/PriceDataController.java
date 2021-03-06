package com.integwise.aggregator.controller;

import java.text.ParseException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.service.PriceDataService;

/**
* The REST API controller, exposes three endpoints.
* GET prices by vendor id
* GET prices by instrument id
* POST prices
* 
* @author Kishor Chukka
* 
*/
@RestController
@RequestMapping("/api/prices")
public class PriceDataController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PriceDataController.class);
	
    private PriceDataService priceDataService;
    
    public PriceDataController(PriceDataService priceDataService) {
    	this.priceDataService = priceDataService;
    }

    @GetMapping("/vendor/{vendorId}")
    public Set<InstrumentPrice> getPricesByVendorId(@PathVariable String vendorId) throws ParseException {
    	LOGGER.info("call getPricesByVendorId {}", vendorId);
        return priceDataService.getPricesByVendorId(vendorId);
    }

    @GetMapping("/instrument/{instrumentId}")
    public Set<InstrumentPrice> getPricesByInstrumentId(@PathVariable String instrumentId) throws ParseException {
    	LOGGER.info("getPricesByInstrumentId {}", instrumentId);
        return priceDataService.getPricesByInstrumentId(instrumentId);
    }
    
    @PostMapping
    public ResponseEntity<String> postPrices(@RequestBody Set<InstrumentPrice> prices) {
		LOGGER.info("postPrices {}", prices);
    	int processed = 0;
    	for(InstrumentPrice price: prices) {
    		try {
	    		priceDataService.addOrUpdate(price);
	    		++processed;
    		} 
    		catch(Exception e) {
    			LOGGER.info("unable to process the message" + e);
    		}
    	}
    	ResponseEntity<String> response = null;
    	if(prices.size() == processed) {
			LOGGER.info("All messages processed successfully");
    		response = new ResponseEntity<String>("All messages processed successfully", HttpStatus.OK);
    	} else {
			LOGGER.info(processed + " messages processed out of "+ prices.size());
    		response = new ResponseEntity<String>(processed + " messages processed out of "+ prices.size(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return response;
    }
}