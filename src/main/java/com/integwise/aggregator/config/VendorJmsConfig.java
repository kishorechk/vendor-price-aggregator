package com.integwise.aggregator.config;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessagingException;

import com.integwise.aggregator.Constants;
import com.integwise.aggregator.domain.InstrumentPrice;
import com.integwise.aggregator.service.PriceDataService;

/**
* Vendor Jms Spring Integration Configuration using Java DSL.
* The integration flow to reads the messages from vendor feed channel, transform and deliver the messages to Price Data Sevice for further processing.
* 
* @author Kishor Chukka
* 
*/
@Configuration
@IntegrationComponentScan
@EnableJms
public class VendorJmsConfig {

	private static final Logger LOGGER =
		      LoggerFactory.getLogger(VendorJmsConfig.class);
	
	@Autowired
    private ConnectionFactory connectionFactory;
	
	@Autowired
	private PriceDataService priceDataService;
	
	@Bean
	public JmsListenerContainerFactory<?> connectionFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		// converter
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	
	@Bean
	public IntegrationFlow vendor1MessageFlow(MessageConverter messageConverter) {
		return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory)
				.jmsMessageConverter(messageConverter)
				.destination(Constants.VENDOR1_PRICE_FEED_CHANNEL)
				.errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
				)
				.transform(Transformers.fromJson(InstrumentPrice.class))
                .handle(m -> priceDataService.addOrUpdate((InstrumentPrice)m.getPayload()))
                .get();
	}
	
	@Bean
	public IntegrationFlow vendor2MessageFlow(MessageConverter messageConverter) {
		return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory)
				.jmsMessageConverter(messageConverter)
				.destination(Constants.VENDOR2_PRICE_FEED_CHANNEL)
				.errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
				)
				.transform(Transformers.fromJson(InstrumentPrice.class))
                .handle(m -> priceDataService.addOrUpdate((InstrumentPrice)m.getPayload()))
                .get();
	}
	
	@Bean
	public IntegrationFlow exceptionMessageFlow(MessageConverter messageConverter) {
		return IntegrationFlows.from(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME
				)
                .handle(m -> {
                	MessagingException exception = (MessagingException)m.getPayload();
                	throw exception;
                	})
                .get();
	}
	
	
}
