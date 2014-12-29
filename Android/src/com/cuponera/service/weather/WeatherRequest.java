package com.cuponera.service.weather;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.settings.Settings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public abstract class WeatherRequest extends AsyncPoolRequest<WeatherResponse> {

	private static Cache<Object, Object> weatherCache;

	static {
		initCache();
	}

	private static void initCache() {
		if (weatherCache == null) {
			weatherCache = CacheBuilder.newBuilder().maximumSize(800).expireAfterAccess(5, TimeUnit.MINUTES).build();
		}
	}

	public static void clearCaches() {

		if (weatherCache != null) {
			weatherCache.invalidateAll();
			weatherCache.cleanUp();
		}
	}

	public WeatherRequest(Context context) {
		super(context);
		initCache();
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

	public boolean isResultCached() {
		String uriHash = "/data/2.5/forecast/daily?lat=" + (int) Settings.getInstance(context).getLatitude() + "&lon="
				+ (int) Settings.getInstance(context).getLongitude() + "lang=es&units=metric&cnt=4";
		WeatherResponse response = (WeatherResponse) weatherCache.getIfPresent(uriHash);

		if (response != null) {
			onServiceReturned(response);
		}

		return response != null;
	}

	private boolean emptyResponse(WeatherResponse response) {
		if (response == null)
			return true;

		if (response.getMainWeather() == null || response.getMainWeather().isEmpty())
			return true;

		return false;
	}

	@Override
	public void onServiceReturned(WeatherResponse result) {
		String uriHash = "/data/2.5/forecast/daily?lat=" + (int) Settings.getInstance(context).getLatitude() + "&lon="
				+ (int) Settings.getInstance(context).getLongitude() + "lang=es&units=metric&cnt=4";
		if (!emptyResponse(result)) {
			weatherCache.put(uriHash, result);
		}

		serviceReady(result);

	}

	protected abstract void serviceReady(WeatherResponse response);

}
