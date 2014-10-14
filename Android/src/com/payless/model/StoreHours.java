package com.payless.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class StoreHours extends BaseModel{
	
	@JsonProperty("Day")
	private String day;

	@JsonProperty("Timing")
	private Timing timing;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Timing getTiming() {
		return timing;
	}

	public void setTiming(Timing timing) {
		this.timing = timing;
	}

	
}
