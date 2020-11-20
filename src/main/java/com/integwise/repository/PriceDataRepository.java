package com.integwise.repository;

import java.text.ParseException;
import java.util.List;

import com.integwise.domain.InstrumentPrice;

public interface PriceDataRepository {
    List<InstrumentPrice> getPricesByVendorId(String vendorId) throws ParseException;
    List<InstrumentPrice> getPricesByInstrumentId(String instrumentId) throws ParseException;
	void addOrUpdate(InstrumentPrice price);
}