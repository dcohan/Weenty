package com.cuponera.service.weather;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.settings.Settings;

public abstract class WeatherRequest extends AsyncPoolRequest<WeatherResponse> {

	public WeatherRequest(Context context) {
		super(context);
	}

	@Override
	public void loadFailed() {
	}

	@Override
	public String getHost() {
		return "api.openweathermap.org";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	public String getPath() {
		return "/data/2.5/forecast/daily?lat=" + (int) Settings.getInstance(context).getLatitude() + "&lon="
				+ (int) Settings.getInstance(context).getLongitude() + "lang=es&units=metric&cnt=4";
	}

	@Override
	protected Class<?> getResponseClass() {
		return WeatherResponse.class;
	}

	@Override
	public boolean isServerTimeOutError() {
		return false;
	}

	@Override
	public boolean isRegularError() {
		return false;
	}

	@Override
	public void loadWasCancelled() {

	}

	@Override
	public Map<String, Object> getUriParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		return params;
	}

	@Override
	public int getLoadingView() {
		return 0;
	}

	@Override
	public void hideLoading() {
		getBaseActivity().hideLoading();
	}

	@Override
	public void showLoading() {
		getBaseActivity().showLoading();
	}

}
