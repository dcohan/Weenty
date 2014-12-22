package com.cuponera.service.weather;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.MainWeather;
import com.cuponera.model.WeatherCity;
import com.cuponera.service.BaseResponse;

public class WeatherResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("city")
	private WeatherCity weatherCity;

	@JsonProperty("list")
	private ArrayList<MainWeather> mainWeather;

	public ArrayList<MainWeather> getMainWeather() {
		return mainWeather;
	}

	public void setMainWeather(ArrayList<MainWeather> mainWeather) {
		this.mainWeather = mainWeather;
	}

	public WeatherCity getWeatherCity() {
		return weatherCity;
	}

	public void setWeatherCity(WeatherCity weatherCity) {
		this.weatherCity = weatherCity;
	}

}
