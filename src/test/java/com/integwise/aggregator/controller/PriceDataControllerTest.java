package com.integwise.aggregator.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.jms.ConnectionFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.integwise.aggregator.Application;
import com.integwise.aggregator.service.PriceDataService;
import com.integwise.aggregator.utils.TestUtils;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PriceDataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PriceDataService priceDataService;
    
    @Autowired
    @Qualifier("jmsConnectionFactory")
    ConnectionFactory jmsConnectionFactory;
    
    @Test
    public void testGetPricesByVendorId() throws Exception {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/vendor/{vendorId}", TestUtils.VENDOR1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].vendorId").value(TestUtils.VENDOR1));
    }

    @Test
    public void testGetPricesByInstrumentId() throws Exception {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/instrument/{instrumentId}", TestUtils.INSTRUMENT1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].instrumentId").value(TestUtils.INSTRUMENT1));
    }
 
}