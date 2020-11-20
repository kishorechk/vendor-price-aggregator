package com.integwise.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.integwise.domain.InstrumentPrice;

@Component
public class H2PriceDataRepository implements PriceDataRepository {
    
    @Override
    public List<InstrumentPrice> getPricesByVendorId(String vendorId) throws ParseException {
        InstrumentPrice p1 = new InstrumentPrice("Vendor1", "Instru1", 99.00, 99.10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-11-18 14:41:10"));
        InstrumentPrice p2 = new InstrumentPrice("Vendor1", "Instru2", 99.00, 99.10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-11-18 14:41:10"));
        List<InstrumentPrice> prices = new ArrayList<>();
        prices.add(p1);
        prices.add(p2);
        return prices;
    }

    @Override
    public List<InstrumentPrice> getPricesByInstrumentId(String instrumentId) throws ParseException {
        InstrumentPrice p1 = new InstrumentPrice("Vendor1", "Instru1", 99.00, 99.10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-11-18 14:41:10"));
        InstrumentPrice p2 = new InstrumentPrice("Vendor2", "Instru1", 99.30, 99.60, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-11-18 14:41:10"));
        List<InstrumentPrice> prices = new ArrayList<>();
        prices.add(p1);
        prices.add(p2);
        return prices;
    }

	@Override
	public void addOrUpdate(InstrumentPrice price) {
		// TODO Auto-generated method stub
		
	}
}