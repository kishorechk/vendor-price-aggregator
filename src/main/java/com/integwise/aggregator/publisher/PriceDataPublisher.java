package com.integwise.aggregator.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.integwise.aggregator.config.ApplicationProperties;
import com.integwise.aggregator.domain.InstrumentPrice;

/**
* PriceDataPublisher publishes the prices on to a JMS topic for downstream clients.
* The consumer jms topic has to be configured in application.properties
* 
* @author Kishor Chukka
* 
*/
@Component
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class PriceDataPublisher {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PriceDataPublisher.class);
  
  @Autowired
  private JmsTemplate jmsTemplate;
  
  private String consumerJmsTopic;

  public PriceDataPublisher(ApplicationProperties appProperties) {
	  this.consumerJmsTopic = appProperties.getConsumerJmsTopic();
  }
  
  public void publish(InstrumentPrice price) {
    LOGGER.info("publishing message='{}'", price);
    jmsTemplate.convertAndSend(consumerJmsTopic, price);
  }
}