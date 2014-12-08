package com.cuponera.service.prehome;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.settings.Settings;

public abstract class PrehomeRequest extends AsyncPoolRequest<PrehomeResponse> {

	public PrehomeRequest(Context context) {
		super(context);
	}

	@Override
	public void loadFailed() {

	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	public String getPath() {
		return "/prehomeimages";
	}

	@Override
	protected Class<?> getResponseClass() {
		return PrehomeResponse.class;
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
		params.put("Latitude", Settings.getInstance(context).getLatitude());
		params.put("Longitude", Settings.getInstance(context).getLongitude());
		return params;
	}

	@Override
	public int getLoadingView() {
		return 0;
	}

	@Override
	public void hideLoading() {
	}

	@Override
	public void showLoading() {
	}

}
