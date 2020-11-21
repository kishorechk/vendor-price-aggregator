package com.integwise.aggregator.config;

import java.io.File;
import java.io.IOException;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.integwise.aggregator.Constants;

@Configuration
@IntegrationComponentScan
@EnableJms
public class VendorFileConfiguration {
	
	@Autowired
    private ConnectionFactory connectionFactory;
	
	public static final String INPUT_DIR = "source";
	public static final String OUTPUT_DIR = "target";
	
	/*@Bean
    public MessageSource<File> sourceDirectory() throws IOException {
		FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new ClassPathResource(INPUT_DIR).getFile());
        return messageSource;
    }*/
	
	private File fileToPoll() throws IOException {
		File file = new ClassPathResource(INPUT_DIR).getFile();
		return file;
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
	
	
	@ServiceActivator(inputChannel = "logging")
	@Bean
	public LoggingHandler loggingHandler() {
	    return new LoggingHandler(LoggingHandler.Level.TRACE);
	}

	@Bean 	
	public StandardIntegrationFlow processFileFlow() { 
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return IntegrationFlows.from("fileInputChannel")
				.transform(fileToStringTransformer())
				.transform(GenericMessage.class, message -> ((String)message.getPayload()).split("[\\r\\n]+"))
				.split().handle(vendor1JmsOutboundGateway(converter)).get();

	}
	
	@Bean
	@InboundChannelAdapter(channel = "fileInputChannel", poller = @Poller(fixedDelay = "10000"))
	public MessageSource<File> vendor2FileReadingMessageSource() throws IOException {
		CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter(Constants.VENDOR2_PRICE_FEED_FILE));
		filters.addFilter(new AcceptOnceFileListFilter());
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(fileToPoll());
		source.setFilter(filters);
		return source;
	}
	
	@Bean
	public JmsOutboundGateway vendor2JmsOutboundGateway(MessageConverter messageConverter) {
		JmsOutboundGateway jmsOutboundGateway = new JmsOutboundGateway();
		jmsOutboundGateway.setConnectionFactory(this.connectionFactory);
		jmsOutboundGateway.setMessageConverter(messageConverter);
		jmsOutboundGateway.setRequestDestinationName(Constants.VENDOR2_PRICE_FEED_CHANNEL);
		return jmsOutboundGateway;
	}
	
	@Bean
	@InboundChannelAdapter(channel = "fileInputChannel", poller = @Poller(fixedDelay = "10000"))
	public MessageSource<File> vendor1FileReadingMessageSource() throws IOException {
		CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter(Constants.VENDOR1_PRICE_FEED_FILE));
		filters.addFilter(new AcceptOnceFileListFilter());
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(fileToPoll());
		source.setFilter(filters);
		return source;
	}
	
	@Bean
	public JmsOutboundGateway vendor1JmsOutboundGateway(MessageConverter messageConverter) {
		JmsOutboundGateway jmsOutboundGateway = new JmsOutboundGateway();
		jmsOutboundGateway.setConnectionFactory(this.connectionFactory);
		jmsOutboundGateway.setMessageConverter(messageConverter);
		jmsOutboundGateway.setRequestDestinationName(Constants.VENDOR1_PRICE_FEED_CHANNEL);
		return jmsOutboundGateway;
	}
}
