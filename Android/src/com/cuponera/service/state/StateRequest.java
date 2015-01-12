package com.cuponera.service.state;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public abstract class StateRequest extends AsyncPoolRequest<StateResponse> {

	public StateRequest(Context context) {
		super(context);
		initCache();
	}

	private static Cache<Object, Object> stateCache;

	static {
		initCache();
	}

	private static void initCache() {
		if (stateCache == null) {
			stateCache = CacheBuilder.newBuilder().maximumSize(500).expireAfterAccess(20, TimeUnit.MINUTES).build();
		}
	}

	public static void clearCaches() {

		if (stateCache != null) {
			stateCache.invalidateAll();
			stateCache.cleanUp();
		}
	}

	public boolean isResultCached() {
		String uriHash = "/state";
		StateResponse response = (StateResponse) stateCache.getIfPresent(uriHash);

		if (response != null) {
			onServiceReturned(response);
		}

		return response != null;
	}

	private boolean emptyResponse(StateResponse response) {
		if (response == null)
			return true;

		if (response.getState() == null || response.getState().isEmpty())
			return true;

		return false;
	}

	@Override
	public void onServiceReturned(StateResponse result) {
		String uriHash = "/state";
		if (!emptyResponse(result)) {
			stateCache.put(uriHash, result);
		}

		serviceReady(result);

	}

	protected abstract void serviceReady(StateResponse response);

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
