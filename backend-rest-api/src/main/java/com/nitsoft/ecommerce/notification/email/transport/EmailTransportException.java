package com.nitsoft.ecommerce.notification.email.transport;

public class EmailTransportException extends RuntimeException {
	
	private static final long serialVersionUID = 959435017940801299L;

	public EmailTransportException(String message, Exception cause) {
		super(message, cause);
	}
}
