package com.integwise.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentPrice extends Entity {
	
	public class InstrumentPriceKey extends Entity{
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
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
            result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
            result = prime * result + ((priceDate == null) ? 0 : priceDate.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            InstrumentPriceKey other = (InstrumentPriceKey) obj;
            
            if (vendorId == null) {
                if (other.vendorId != null)
                    return false;
            } else if (!vendorId.equals(other.vendorId))
                return false;
            if (instrumentId == null) {
                if (other.instrumentId != null)
                    return false;
            } else if (!instrumentId.equals(other.instrumentId))
                return false;
            if (priceDate == null) {
                if (other.priceDate != null)
                    return false;
            } else if (!priceDate.equals(other.priceDate))
                return false;
            return true;
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
    private Date creationDate;

    @JsonCreator
    public InstrumentPrice(@JsonProperty("vendorId") final String vendorId,
    		@JsonProperty("instrumentId") final String instrumentId,
    		@JsonProperty("bidPrice") final BigDecimal bidPrice,
    		@JsonProperty("askPrice") final BigDecimal askPrice,
    		@JsonProperty("priceDate") final Date priceDate) 
    {
        this.key = new InstrumentPriceKey(vendorId, instrumentId, priceDate);
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
    
    @Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((bidPrice == null) ? 0 : bidPrice.hashCode());
        result = prime * result + ((askPrice == null) ? 0 : askPrice.hashCode());
		return result;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        InstrumentPrice other = (InstrumentPrice) obj;
        
        if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (bidPrice == null) {
			if (other.bidPrice != null)
				return false;
		} else if (!bidPrice.equals(other.bidPrice))
			return false;
        if (askPrice == null) {
            if (other.askPrice != null)
                return false;
        } else if (!askPrice.equals(other.askPrice))
            return false;
		return true;
    }
    
    @Override
	public String toString() {
        return "InstrumentPrice [key=" + key 
            + ", bidPrice=" + bidPrice 
            + ", askPrice=" + askPrice + "]";
	}
}