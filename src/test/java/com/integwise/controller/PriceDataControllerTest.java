package com.integwise.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceDataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    String pricesByVendorIdResponse = "[{\"vendorId\":\"Vendor1\",\"instrumentId\":\"Instru1\",\"bidPrice\":99.0,\"askPrice\":99.1,\"priceDate\":\"2020-11-18T14:41:10.000+00:00\"},{\"vendorId\":\"Vendor1\",\"instrumentId\":\"Instru2\",\"bidPrice\":99.0,\"askPrice\":99.1,\"priceDate\":\"2020-11-18T14:41:10.000+00:00\"}]";
    String pricesByInstrumentId = "[{\"vendorId\":\"Vendor1\",\"instrumentId\":\"Instru1\",\"bidPrice\":99.0,\"askPrice\":99.1,\"priceDate\":\"2020-11-18T14:41:10.000+00:00\"},{\"vendorId\":\"Vendor2\",\"instrumentId\":\"Instru1\",\"bidPrice\":99.3,\"askPrice\":99.6,\"priceDate\":\"2020-11-18T14:41:10.000+00:00\"}]";
    
    @Test
    public void getPricesByVendorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/vendor/{vendorId}", "Vendor1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(pricesByVendorIdResponse)));
    }

    @Test
    public void getPricesByInstrumentId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices/instrument/{instrumentId}", "Instr1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(pricesByInstrumentId)));
    }
}