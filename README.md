## Vendor Price Aggregator

The vendor price aggregator aggregates price information for instruments and make it available for the downstream clients.

The system supports the following features:
1. Process the real time price updates for instruments from different vendors
2. Cache the prices amd store the prices in local store. Cache will have prices only for last 30 days
3. Publish price updates to the downstream clients
    * all instrument prices by vendor
    * all vendor prices by instrument
4. Web services for downstream clients to access the prices in real time
    * all instrument prices by vendor
    * all vendor prices by instrument

### High Level FLow:

![High Level Flow](./images/highlevel.png)

### Message Channels and Flow

One Pub-sub Channel per vendor - Each vendor has a designated channel for the modified price data. This way, the original price data remains intact and each application can listen to its specific vendor Message Channel for the modified price updates. The channel type is a JMS topic so that the vendor price data can be directly used by other consumers if required.

One Pub-Sub channel to publish the aggregated pries for Clients to consume. This allows the interested clients to subscribe for price updates. Channel type is a JMS Topic to allow interested downstrean clients can subscribe.

#### Sequence Diagram

The vendor price updates will be processed as shown below:

![Vendor Sequence Diagram](./images/vendor_price_updates_sequence.png)

### Entity Model

* Instrument Price entity consists fields - instrumentId, vendorId, bidPrice, askPrice, priceDate. 
* Composit unique key - (instrument id, vendor id, priceDate) 
* The creation date will be set for each record when storing in local store, cache. This field will be used for archiving the old data.

![Entity Model](./images/entity-model.png)

The solution initially supports JSON message format for vendor and clients. The JSON format has been chosen due its lightweight format and easy to marshal and unmarshal. The solution can be extended to support other formats like XML, CSV by building transformer to process specific message format. 

### Store
The solution stores the prices in local store which will be updated for all the price updates in realtime. The solution currently uses in-memory hashmap which can be switched to actual database in future.

### Cache
The solution maintains a local cache (ehCache) of prices which will be updated for all the price updates in real time. The cache eviction policy setup to delete the records older than 30 days. There will be no data older than 30 days in the cache. If there is no feed received for >30 days then the system cannot provide one to a client request.
 
### Webservices
The solution offers REST API endpoints to fetch prices -
* all instrument prices by vendor
* all vendor prices by instrument

The REST API will fetch the data from cache for better performance.

Consumer can access the pricing API to fetch the prices as shown below:
![Consumer API Sequence Diagram](./images/consumer_api_sequence.png)
