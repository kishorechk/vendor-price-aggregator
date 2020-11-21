package com.integwise.aggregator.exception;

public class UnsupportedDatastoreTypeException extends RuntimeException {
	public UnsupportedDatastoreTypeException(String message) {
		super(message);
	}
	
	public UnsupportedDatastoreTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
