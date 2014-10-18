package com.cuponera.yahoo;
/**
 * A callback when querying is completed.
 * @author Zhenghong Wang
 */
public interface YahooWeatherInfoListener {
	public void gotWeatherInfo(WeatherInfo weatherInfo);
}
