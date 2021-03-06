## Vendor Price Aggregator

The vendor price aggregator supports the following features:
1. Process the price updates in real time for instruments from different vendors
2. Cache the prices add store the prices in local store. Cache will have the prices for last 30 days.
3. Publish the price updates in real time to the downstream clients
4. Web services (REST API) to fetch the prices -
    * all instrument prices by vendor
    * all vendor prices by instrument

### Assumptions:
Based on the email clarifications from the team, I have made the below assumptions to implement this PoC - 
* Entity model consists few key fields but the real instrument price data will have more fields.
* JSON message format used due to its lightweight nature and easy to parsing. In case of Vendor/Consumers requires differnt message format and structure, the solution can be extended by adding new channels and transformers to the integration flow.
* The cache is configured to delete the records older than 30 days. If there is no feed received for in last 30 days then the system cannot provide one to a client request.
* Spring Boot uses default profiles, this can be extended to add env specific profles.
* No Authentication/Authorization for Client REST APIs has been added due to time constraints. We can use OAuth2 framework to restrict the access based on the business requirements.  
* Basic API documentation has been added using Swagger, this can be extended further to add detailed documentation.

### High Level FLow:

![High Level Flow](./images/highlevel.png)

#### Sequence Diagram

The vendor price updates will be processed as shown below at high level. The Spring Integration Flows section has more details about all the Pipes & Filters.

![Vendor Sequence Diagram](./images/vendor_price_updates_sequence.png)

### Message Channels

**One Pub-sub Channel per vendor** - Each vendor has a designated channel for the modified price data. This way, the original price data remains intact and each application can listen to its specific vendor Message Channel for the modified price updates.

**One Pub-Sub channel** to publish the aggregated prices for Clients to consume. This way, all the interested clients to subscribe for price updates.

### Spring Integration (Java DSL) Flows 
#### Vendor Jms Integration Flow

This integration flow show case how to read vendor prices published in real time via a messaging channel. The solution uses Spring Integration (Java DSL) to implement to this flow.

![Vendor Jms Integration Flow](./images/VendorJmsIntegrationFlow.png)

#### Vendor File Integration Flow

This integration flow show case read vendor prices from a file and publish them as messages. The solution uses Spring Integration (Java DSL) to implement to this flow. The poller is config to poll the source dir for evey 10000ms and process the file only once.

![Vendor File Integration Flow](./images/VendorFileIntegrationFlow.png)

### Message format
The solution initially supports JSON message format for vendor and clients.

Sample message
```
{"vendorId": "Vendor1", "instrumentId": "APPL", "bidPrice": 100.30, "askPrice": 101.10, "priceDate": "2020-11-21T10:20:22"}
```

### Entity Model

* Instrument Price entity consists fields - instrumentId, vendorId, bidPrice, askPrice, priceDate.
* Composite unique key - (instrument id, vendor id, priceDate)

![Entity Model](./images/entity-model.png)

### Data Store
The solution currently uses in-memory maps stores the prices. The datastore can be switched to different type with configuration change and implementing required DAO logic.
```
app.aggregator.data-store-type=MAPDB
```

### Cache
The solution uses Ehcache to maintains a local cache of prices. The cache is configured to delete the records older than 30 days and it holds maximum of 10000 entries. If there is no feed received for in last 30 days then the system cannot provide one to a client request.
```
app.aggregator.cache-time-to-live-days=30
app.aggregator.cache-max-entries=100000
```
 
### Webservices
The solution offers REST API GET endpoints to fetch prices -
```
* GET /api/prices/vendor/{vendorId}
* GET /api/prices/instrument/{instrumentId}
```

The REST API GET services will fetch the data from cache for better performance. If any cache miss, the data will fetched from store and updates the cache.

Consumer can access the pricing API to fetch the prices as shown below:
![Consumer API Sequence Diagram](./images/consumer_api_sequence.png)

The solution also offers a POST service which can be used to insert new instrument prices. This can be used for demo purposes.
```
POST /api/prices

[{"vendorId": "Vendor1", "instrumentId": "APPL", "bidPrice": 100.30, "askPrice": 101.10, "priceDate": "2020-11-21T10:20:22"},
{"vendorId": "Vendor1", "instrumentId": "GOOG", "bidPrice": 100.30, "askPrice": 101.10, "priceDate": "2020-11-21T10:20:22"}]
```

### Run Local

Run the below commands from the command line:

```
git clone https://github.com/kishorechk/vendor-price-aggregator.git 

cd vendor-price-aggregator 

./mvnw spring-boot:run
```
The command starts the Spring Boot application which includes integration flows and REST services and Swagger docs and UI for API documentation.

* REST API Endpoint - http://localhost:8080
* REST API Documentation - http://localhost:8080/swagger-ui/index.html#/price-data-controller

#### Validation
The application loads the prices from vendor input files from classpath. The below REST API endpoints can be used for validation - 

```
GET http://localhost:8080/api/prices/vendor/Vendor1
GET http://localhost:8080/api/prices/instrument/GOOG
```
