package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class MainWeather extends BaseModel {

	@JsonProperty("temp")
	private double temp;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("humidity")
	private double humidity;

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

}
