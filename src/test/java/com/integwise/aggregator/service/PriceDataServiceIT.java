package com.integwise.aggregator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.integwise.aggregator.Application;
import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.utils.TestUtils;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PriceDataServiceIT {

    @Autowired
    private PriceDataService priceDataService;

    @Test
    public void getPricesByVendorId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByVendorId(TestUtils.vendor1);
        assertFalse(prices.isEmpty());
        assertEquals(2, prices.size());
    }

    @Test
    public void getPricesByInstrumentId() {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        Set<InstrumentPrice> prices = priceDataService.getPricesByVendorId(TestUtils.instrument1);
        assertFalse(prices.isEmpty());
        assertEquals(2, prices.size());
    }

    @Test
    public void addOrUpdate() {

    }
}