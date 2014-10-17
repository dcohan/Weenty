package com.cuponera.service;

import org.codehaus.jackson.annotate.JsonProperty;

public class ErrorModelResponse extends BaseResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7611786201580322728L;

	@JsonProperty("Code")
	private  String code;

	@JsonProperty("Message")
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
