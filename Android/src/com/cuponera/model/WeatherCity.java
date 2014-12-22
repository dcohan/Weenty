package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class WeatherCity extends BaseModel {

	@JsonProperty("name")
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
