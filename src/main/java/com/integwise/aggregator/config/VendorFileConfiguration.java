package com.integwise.aggregator.config;

import java.io.File;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import com.integwise.aggregator.Constants;

@Configuration
@IntegrationComponentScan
@EnableJms
public class VendorFileConfiguration {
	
	@Autowired
    private ConnectionFactory connectionFactory;
	
	public static final String INPUT_DIR = "/Users/kishorchukka/dev/spring/mizuho/vendor-price-aggregator/src/main/resources/source";
	public static final String OUTPUT_DIR = "target";
	
	@Bean
    public MessageSource<File> sourceDirectory() {
		FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(INPUT_DIR));
        return messageSource;
    }
	
	/*@Bean
    public MessageHandler targetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setExpectReply(false); // end of pipeline, reply not needed
        return handler;
    }*/
	
	@Bean
    public GenericSelector<File> onlyJsons() {
        return (file) -> file.getName().endsWith(".txt");
    }
		
	@Bean
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }
	
	
	/*@Bean 	
	public StandardIntegrationFlow processFileFlow() { 
		return IntegrationFlows.from("fileInputChannel").split().handle(vendor1JmsOutboundGateway()).get();

	}
	
	@Bean
	public IntegrationFlow vendor1FileIntegrationFlow() {
		return IntegrationFlows.from(sourceDirectory(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))
				.channel("fileInputChannel")
        .filter(onlyJsons())
        .transform(fileToStringTransformer())
        .transform(GenericMessage.class, message -> ((String)message.getPayload()).split("[\\r\\n]+"))
        .split()
        .handle(vendor1JmsOutboundGateway())
        .get();
	}
	
	
	@Bean
	public JmsOutboundGateway vendor1JmsOutboundGateway() {
		JmsOutboundGateway jmsOutboundGateway = new JmsOutboundGateway();
		jmsOutboundGateway.setConnectionFactory(this.connectionFactory);
		jmsOutboundGateway.setRequestDestinationName(Constants.VENDOR1_PRICE_FEED_CHANNEL);
		return jmsOutboundGateway;
	}*/
}
