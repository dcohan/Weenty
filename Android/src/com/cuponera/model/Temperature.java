package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Temperature extends BaseModel {

	@JsonProperty("day")
	private double day;

	@JsonProperty("min")
	private double min;

	@JsonProperty("max")
	private double max;

	public double getDay() {
		return day;
	}

	public void setDay(double day) {
		this.day = day;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

}
