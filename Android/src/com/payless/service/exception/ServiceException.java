package com.payless.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 2402843343418555093L;
	protected Exception originalException;

	public ServiceException(Exception originalException) {
		this.originalException = originalException;
	}

	public String getMessage() {
		return originalException.getMessage();
	}

	public Exception getOriginalException() {
		return originalException;
	}

}
