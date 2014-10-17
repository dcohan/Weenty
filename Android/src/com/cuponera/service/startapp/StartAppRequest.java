package com.cuponera.service.startapp;

import java.util.Map;

import android.content.Context;

import com.cuponera.pool.PaylessAsyncPoolRequest;
import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;

public abstract class StartAppRequest extends PaylessAsyncPoolRequest<StartAppResponse> {

	public StartAppRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/StartApp";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	protected Class<?> getResponseClass() {
		return StartAppResponse.class;
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
		return null;
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
