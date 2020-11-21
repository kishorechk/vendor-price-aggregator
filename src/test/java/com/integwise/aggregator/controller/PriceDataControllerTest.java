package com.integwise.aggregator.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Test
    public void getPricesByVendorId() throws Exception {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/vendor/{vendorId}", TestUtils.vendor1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].vendorId").value(TestUtils.vendor1));
    }

    @Test
    public void getPricesByInstrumentId() throws Exception {
        TestUtils.getAllPrices().forEach(price -> priceDataService.addOrUpdate(price));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/instrument/{instrumentId}", TestUtils.instrument1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].instrumentId").value(TestUtils.instrument1));
    }
 
}