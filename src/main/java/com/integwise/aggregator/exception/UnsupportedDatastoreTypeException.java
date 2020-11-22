package com.integwise.aggregator.exception;

/**
* Custom exception class, this exception will be thrown in case invalid datastore configuration.
* 
* @author Kishor Chukka
* 
*/
public class UnsupportedDatastoreTypeException extends RuntimeException {
	public UnsupportedDatastoreTypeException(String message) {
		super(message);
	}
	
	public UnsupportedDatastoreTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
