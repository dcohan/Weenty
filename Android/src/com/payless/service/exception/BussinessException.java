package com.payless.service.exception;

public class BussinessException extends ServiceException {

	private static final long serialVersionUID = 8262038965870980702L;

	public BussinessException(Exception originalException) {
		super(originalException);
	}

}
