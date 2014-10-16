package com.payless.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Timing extends BaseModel {

	@JsonProperty("Close")
	private String close;

	@JsonProperty("Open")
	private String open;

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}
