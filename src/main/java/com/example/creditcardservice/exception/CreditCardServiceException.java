package com.example.creditcardservice.exception;


/**
 * Exception class for CreditCardService
 * @author javadevopsmc06
 *
 */
public class CreditCardServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 8640036025651169404L;

	public CreditCardServiceException() {
		
	}
	
	public CreditCardServiceException(String message) {
		super(message);
	}
	
	public CreditCardServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
