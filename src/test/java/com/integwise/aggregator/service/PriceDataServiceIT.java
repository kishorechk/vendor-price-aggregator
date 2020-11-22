package com.integwise.aggregator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.jms.core.JmsTemplate;

import com.integwise.aggregator.Application;
import com.integwise.aggregator.Constants;
import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.utils.TestUtils;

@SpringBootTest(classes = Application.class)
public class PriceDataServiceIT {

    @Autowired
    private PriceDataService priceDataService;

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private CacheManager cacheManager;
    
    @Test
    public void testGetPricesByVendorId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByVendorId(TestUtils.VENDOR1);
        assertFalse(prices.isEmpty());
        assertEquals(4, prices.size());
    }

    @Test
    public void testGetPricesByInstrumentId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByInstrumentId(TestUtils.INSTRUMENT1);
        assertFalse(prices.isEmpty());
        assertEquals(2, prices.size());
    }
    
    @Test
    public void testGetPricesByInvalidVendorId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByVendorId("1");
        assertTrue(prices.isEmpty());
    }

    @Test
    public void testGetPricesByInvalidInstrumentId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByInstrumentId("1");
        assertTrue(prices.isEmpty());
    }

    @Test
    public void testPublishMessageToConsumerTopic() {
        priceDataService.publish(TestUtils.i1);
        this.jmsTemplate.setReceiveTimeout(10_0000);
        InstrumentPrice price = (InstrumentPrice) this.jmsTemplate.receiveAndConvert("consumer-prices-pub-sub-topic");
        assertTrue(TestUtils.i1.equals(price));
    }
    
    @Test
    public void testCacheAvailability() {
    	TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
    	assertNotNull(cacheManager.getCache(Constants.GET_PRICES_BY_VENDOR_CACHE).get(TestUtils.VENDOR1).get());
    }
}