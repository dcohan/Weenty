package com.payless.service.exception;

public class ServerErrorException extends ServiceException {

	private static final long serialVersionUID = 4181411342373096587L;

	public ServerErrorException(HttpServiceException originalException) {
		super(originalException);
	}

	public int getErroCode() {
		return ((HttpServiceException) originalException).getCode();
	}
}