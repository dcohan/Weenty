package com.cuponera.service.state;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;

public abstract class StateRequest extends AsyncPoolRequest<StateResponse> {

	public StateRequest(Context context) {
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
		return "/state";
	}

	@Override
	protected Class<?> getResponseClass() {
		return StateResponse.class;
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
	}

	@Override
	public void showLoading() {
	}

}
