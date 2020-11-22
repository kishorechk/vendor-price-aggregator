package com.integwise.aggregator.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.integwise.aggregator.domain.InstrumentPrice;

public class TestUtils {

	public final static long DAY_IN_MS = 1000 * 60 * 60 * 24;

	public final static Date DATE_31_DAYS_AGO = new Date(System.currentTimeMillis() - (31 * DAY_IN_MS));
	public final static Date DATE_30_DAYS_AGO = new Date(System.currentTimeMillis() - (30 * DAY_IN_MS));
	public final static Date DATE_20_DAYS_AGO = new Date(System.currentTimeMillis() - (20 * DAY_IN_MS));
	public final static Date DATE_7_DAYS_AGO = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
	public final static Date DATE_1_DAY_AGO = new Date(System.currentTimeMillis() - (1 * DAY_IN_MS));
	
	public final static String VENDOR1 = "Vendor1";
	public final static String VENDOR2 = "Vendor2";
	
	public final static String INSTRUMENT1 = "GOOG";
	public final static String INSTRUMENT2 = "APPL";
	public final static String INSTRUMENT3 = "MSFT";

	public static InstrumentPrice i1 = new InstrumentPrice(VENDOR1, INSTRUMENT1, new BigDecimal("100.89"), new BigDecimal("100.60"), DATE_7_DAYS_AGO);
	public static  InstrumentPrice i2 = new InstrumentPrice(VENDOR1, INSTRUMENT2, new BigDecimal("200.89"), new BigDecimal("200.60"), DATE_1_DAY_AGO);
	public static InstrumentPrice i3 = new InstrumentPrice(VENDOR1, INSTRUMENT3, new BigDecimal("2000.89"), new BigDecimal("2000.60"), DATE_30_DAYS_AGO);
	public static InstrumentPrice i4 = new InstrumentPrice(VENDOR2, INSTRUMENT1, new BigDecimal("100.89"), new BigDecimal("100.60"), DATE_7_DAYS_AGO);
	public static InstrumentPrice i5 = new InstrumentPrice(VENDOR2, INSTRUMENT2, new BigDecimal("200.89"), new BigDecimal("200.60"), DATE_31_DAYS_AGO);
	public static InstrumentPrice i6 = new InstrumentPrice(VENDOR2, INSTRUMENT3, new BigDecimal("2000.89"), new BigDecimal("2000.60"), DATE_30_DAYS_AGO);
	
	public static Set<InstrumentPrice> getAllPrices() {
		return new HashSet<>(Arrays.asList(i1, i2, i3, i4, i5, i6));
	}
}
