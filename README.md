## Vendor Price Aggregator

The vendor price aggregator supports the following features:
1. Process the price updates in real time for instruments from different vendors
2. Cache the prices add store the prices in local store. Cache will have the prices for last 30 days.
3. Publish the price updates in real time to the downstream clients
4. Web services (REST API) to fetch the prices -
    * all instrument prices by vendor
    * all vendor prices by instrument

### High Level FLow:

![High Level Flow](./images/highlevel.png)

### Message Channels and Flow

One Pub-sub Channel per vendor - Each vendor has a designated channel for the modified price data. This way, the original price data remains intact and each application can listen to its specific vendor Message Channel for the modified price updates. The channel type is a ActiveMQ topic so that the vendor price data can be directly used by other consumers if required.

One Pub-Sub channel to publish the aggregated pries for Clients to consume. This allows the interested clients to subscribe for price updates. The channel type is a ActiveMQ topic to allow interested downstrean clients can subscribe.

### Message format
The solution initially supports JSON message format for vendor and clients. The JSON format has been chosen due its lightweight format and easy to marshal and unmarshal. The solution can be extended to support other formats like XML, CSV by building transformer to process specific message format. 

Sample message
```
{"vendorId": "Vendor1", "instrumentId": "APPL", "bidPrice": 100.30, "askPrice": 101.10, "priceDate": "2020-11-21T10:20:22"}
```
#### Sequence Diagram

The vendor price updates will be processed as shown below -

![Vendor Sequence Diagram](./images/vendor_price_updates_sequence.png)

Spring Integration flows configured to process the messages - update cache, update local store and publish prices to consumers.

### Entity Model

* Instrument Price entity consists fields - instrumentId, vendorId, bidPrice, askPrice, priceDate. 
* Composite unique key - (instrument id, vendor id, priceDate)

![Entity Model](./images/entity-model.png)

### Store
The solution stores the prices in local store to maintain aggregate prices. The solution currently uses in-memory maps which can be switched to actual database in future.

### Cache
The solution uses Ehcache to maintains a local cache of prices. The cache is configured to delete the records older than 30 days. If there is no feed received for in last 30 days then the system cannot provide one to a client request.
 
### Webservices
The solution offers REST API endpoints to fetch prices -
* all instrument prices by vendor
* all vendor prices by instrument

The REST API will fetch the data from cache for better performance. If any cache miss, the data will fetched from store and updates the cache.

Consumer can access the pricing API to fetch the prices as shown below:
![Consumer API Sequence Diagram](./images/consumer_api_sequence.png)

### Technologies Used
The system is designed as a microservice using the following technologies:
* Java – versions 1.8
* Spring – including Spring boot, Spring integration, Spring Web.
* JMS – ActiveMQ
* Caching – Ehcache
* Store - in-memory map
* Testing frameworks – JUnit/Mockito
* Maven

### How to run

```
git clone https://github.com/kishorechk/vendor-price-aggregator.git 

cd vendor-price-aggregator 

./mvnw spring-boot:run
```

### Swagger UI


http://localhost:8080/swagger-ui/index.html#/price-data-controller
