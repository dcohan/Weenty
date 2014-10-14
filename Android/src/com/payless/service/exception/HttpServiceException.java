package com.payless.service.exception;

public class HttpServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2423385959329565467L;
	private int code;
	private String message;

	public HttpServiceException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
