package com.integwise.aggregator.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
* InstrumentPrice is the main entity we'll be using to store instrument price.
* 
* The class InstrumentPriceKey is defined nested as vendorId, instrumentId and priceDate fields form a composite key.
* 
* @author Kishor Chukka
* 
*/
public class InstrumentPrice implements Entity {
    
    //composite key, inner class
	public class InstrumentPriceKey implements Entity{
        private String vendorId;
        private String instrumentId;
        private Date priceDate;

        public InstrumentPriceKey(String vendorId,
            String instrumentId,
            Date priceDate) 
        {
            this.vendorId = vendorId;
            this.instrumentId = instrumentId;
            this.priceDate = priceDate;
        }

        public String getVendorId() {
            return vendorId;
        }
    
        public String getInstrumentId() {
            return instrumentId;
        }

        public Date getPriceDate() {
            return priceDate;
        }

        @Override
        public int hashCode() {
        	return Objects.hash(instrumentId, vendorId, priceDate);
        }
        
        @Override
        public boolean equals(Entity obj) {
        	if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            InstrumentPriceKey that = (InstrumentPriceKey) obj;
            return Objects.equals(instrumentId, that.instrumentId) &&
                    Objects.equals(vendorId, that.vendorId) &&
                    Objects.equals(priceDate, that.priceDate);
        }
        
        @Override
        public String toString() {
            return "InstrumentPrice [vendorId=" + vendorId 
                + ", instrumentId=" + instrumentId 
                + ", priceDate=" + priceDate + "]";
        }
    }
	
	@JsonIgnore
    private InstrumentPriceKey key;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private String vendorId;
    private String instrumentId;
    private Date priceDate;

    @JsonCreator
    public InstrumentPrice(@JsonProperty("vendorId") final String vendorId,
    		@JsonProperty("instrumentId") final String instrumentId,
    		@JsonProperty("bidPrice") final BigDecimal bidPrice,
    		@JsonProperty("askPrice") final BigDecimal askPrice,
    		@JsonProperty("priceDate") final Date priceDate) 
    {
        this.key = new InstrumentPriceKey(vendorId, instrumentId, priceDate);
        this.vendorId = vendorId;
        this.instrumentId = instrumentId;
        this.priceDate = priceDate;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public InstrumentPriceKey getKey() {
        return key;
    }
    
    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public BigDecimal getAskPrice() {
        return this.askPrice;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public String getInstrumentId() {
        return this.instrumentId;
    }

    public Date getPriceDate() {
        return this.priceDate;
    }
    
    @Override
	public int hashCode() {
    	return Objects.hash(instrumentId, vendorId, bidPrice, askPrice, priceDate);
    }
    
	@Override
	public boolean equals(Entity obj) {
		 if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        InstrumentPrice that = (InstrumentPrice) obj;
	        return Objects.equals(instrumentId, that.instrumentId) &&
	                Objects.equals(vendorId, that.vendorId) &&
	                Objects.equals(bidPrice, that.bidPrice) &&
	                Objects.equals(askPrice, that.askPrice) &&
	                Objects.equals(priceDate, that.priceDate);
    }
    
    @Override
	public String toString() {
        return "InstrumentPrice [key=" + key 
            + ", bidPrice=" + bidPrice 
            + ", askPrice=" + askPrice + "]";
	}
}