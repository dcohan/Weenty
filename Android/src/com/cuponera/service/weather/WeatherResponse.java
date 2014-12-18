package com.cuponera.service.weather;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.MainWeather;
import com.cuponera.model.Weather;
import com.cuponera.service.BaseResponse;

public class WeatherResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("weather")
	private ArrayList<Weather> weather;

	@JsonProperty("main")
	private MainWeather mainWeather;

	public ArrayList<Weather> getWeather() {
		return weather;
	}

	public void setWeather(ArrayList<Weather> weather) {
		this.weather = weather;
	}

	public MainWeather getMainWeather() {
		return mainWeather;
	}

	public void setMainWeather(MainWeather mainWeather) {
		this.mainWeather = mainWeather;
	}

}
