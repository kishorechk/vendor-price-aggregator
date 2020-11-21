package com.integwise.aggregator.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.integwise.aggregator.Application;
import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.utils.TestUtils;

public class MapDataStoreTests {
	
	
    private MapDataStore mapDataStore;
    
    @BeforeEach
    void initUseCase() {
    	mapDataStore = new MapDataStore();
    }

    @Test
    public void test_get() {
        mapDataStore.addOrUpdate(TestUtils.i1.getKey(), TestUtils.i1);
        Set<InstrumentPrice> prices = mapDataStore.getAll();
        assertFalse(prices.isEmpty());

        InstrumentPrice price = (InstrumentPrice) mapDataStore.get(TestUtils.i1.getKey());
        assertTrue(price.equals(TestUtils.i1));
    }
    
    @Test
    public void test_getAll() {
        mapDataStore.addOrUpdate(TestUtils.i1.getKey(), TestUtils.i1);
        mapDataStore.addOrUpdate(TestUtils.i2.getKey(), TestUtils.i2);
        mapDataStore.addOrUpdate(TestUtils.i3.getKey(), TestUtils.i3);
        Set<InstrumentPrice> prices = mapDataStore.getAll();
        assertFalse(prices.isEmpty());
        assertEquals(3, prices.size());
    }
    
    @Test
    public void test_delete() {
        mapDataStore.addOrUpdate(TestUtils.i1.getKey(), TestUtils.i1);
        mapDataStore.delete(TestUtils.i1.getKey());
        Set<InstrumentPrice> prices = mapDataStore.getAll();
        assertTrue(prices.isEmpty());
    }
    
    @Test
    public void test_addOrUpdate() {
        mapDataStore.addOrUpdate(TestUtils.i1.getKey(), TestUtils.i1);
        Set<InstrumentPrice> prices = mapDataStore.getAll();
        assertFalse(prices.isEmpty());
        assertEquals(1, prices.size());
    }
}
