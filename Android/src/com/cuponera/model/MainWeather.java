package com.cuponera.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class MainWeather extends BaseModel {

	@JsonProperty("temp")
	private Temperature temp;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("humidity")
	private double humidity;

	@JsonProperty("weather")
	private ArrayList<Weather> weather;

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

	public ArrayList<Weather> getWeather() {
		return weather;
	}

	public void setWeather(ArrayList<Weather> weather) {
		this.weather = weather;
	}

	public Temperature getTemp() {
		return temp;
	}

	public void setTemp(Temperature temp) {
		this.temp = temp;
	}

}
