package com.integwise.domain;

import java.util.Date;

public class InstrumentPrice extends Entity {
    private String vendorId;
    private String instrumentId;
    private Double bidPrice;
    private Double askPrice;
    private Date priceDate;

    public InstrumentPrice(String vendorId,
        String instrumentId,
        Double bidPrice,
        Double askPrice,
        Date priceDate) 
    {
        this.vendorId = vendorId;
        this.instrumentId = instrumentId;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.priceDate = priceDate;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Double getAskPrice() {
        return this.askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Date getPriceDate() {
        return priceDate;
    }
    
    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
        result = prime * result + ((bidPrice == null) ? 0 : bidPrice.hashCode());
        result = prime * result + ((askPrice == null) ? 0 : askPrice.hashCode());
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
        InstrumentPrice other = (InstrumentPrice) obj;
        
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
            + ", bidPrice=" + bidPrice 
            + ", askPrice=" + askPrice 
            + ", priceDate=" + priceDate + "]";
	}
}