package com.cuponera.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.event.LoaderEvent;
import com.cuponera.service.HttpService;
import com.cuponera.service.ServiceConfig;
import com.cuponera.ui.loader.LoaderInfo;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ValidationUtils;

public class AsyncPoolManager {

	private static AsyncPoolManager mInstance = null;
	private ArrayList<AsyncPoolLoader> loaders;
	private boolean throwErrorMessage = false;
	private boolean callbackHandledError = false;
	private Context context;

	private AsyncPoolManager() {

	}

	private AsyncPoolManager(Context context) {
		this.setContext(context);
	}

	public static AsyncPoolManager getInstance(Context context) {
		if (mInstance == null || mInstance.getContext() != context) {
			mInstance = new AsyncPoolManager(context);
		}

		return mInstance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void executeLoaderWith(BaseAsyncPoolRequest request, boolean withUriParams) {

		Map<String, Object> uriParams = getDefaultParamsAppendingList(request.getUriParams(), request.getContext(), withUriParams);

		AsyncPoolLoader loader = new AsyncPoolLoader(getContext(), request.getHttpMethod(), uriParams, request.getProtocol(), request.getHost(),
				request.getPath());

		if (loaders == null) {
			loaders = new ArrayList<AsyncPoolLoader>();
		}

		loader.setLoadingView(request.getLoadingView());
		loader.setCallback(request);

		showLoading(loader);

		loaders.add(loader);

		((Activity) getContext()).getLoaderManager().restartLoader(loaders.size(), null, loader);
	} 

	public void loaderReturns(String result, AsyncPoolLoader loader) {
		if (loaders == null || loaders.indexOf(loader) < 0) {
			return;
		}

		loaders.remove(loaders.indexOf(loader));

		Log.i("Async Pool Manager", result);

		try {
			if (result.equals("no_internet")) {
				throw new Exception();
			}
			loader.getCallback().processResponse(result);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throwErrorMessage = true;

		} catch (Exception e) {
			e.printStackTrace();

			if (!Utils.hasInternetConnection(context)) {

				throwErrorMessage = true;
				callbackHandledError = true;
				EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_INTERNET_ERROR));

			} else {

				if (ValidationUtils.isNullOrEmpty(result)) {
					if (!loader.getCallback().isRegularError()) {
						if (loaders.size() == 0) {
							showServerErrorPopUp();
						} else {
							throwErrorMessage = true;
						}
					} else {
						throwErrorMessage = true;
						callbackHandledError = true;
					}
				}

				if (result.equals(HttpService.TIMEOUT) || result.equals(HttpService.NO_INTERNET)) {
					if (!loader.getCallback().isServerTimeOutError()) {
						if (loaders.size() == 0) {
							showServerErrorPopUp();
						} else {
							throwErrorMessage = true;
						}
					} else {
						throwErrorMessage = true;
						callbackHandledError = true;
					}
				}
			}

			loader.getCallback().loadFailed();

		} finally {
			hideLoading(loader);
		}

		if (loaders.size() == 0 && throwErrorMessage) {
			if (callbackHandledError) {
				callbackHandledError = false;
			} else {
				showServerErrorPopUp();
			}

			loader.getCallback().loadFailed();
		}
	}

	public int getPoolCount() {
		if (loaders == null) {
			loaders = new ArrayList<AsyncPoolLoader>();
		}

		return loaders.size();
	}

	private void showServerErrorPopUp() {
		throwErrorMessage = false;
		EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.SYSTEM_SERVER_ERROR));
	}

	private Map<String, Object> getDefaultParams(Context context) {
		Map<String, Object> defaultParams = new HashMap<String, Object>();

		defaultParams.put("DeviceId", ServiceConfig.getDeviceId());
		defaultParams.put("AppVersion", Utils.getAppVersion(context));
		defaultParams.put("ResolutionWidth", Utils.getScreen(context).x);
		defaultParams.put("ResolutionHeight", Utils.getScreen(context).y);

		return defaultParams;
	}

	private Map<String, Object> getDefaultParamsAppendingList(Map<String, Object> params, Context context, boolean withParams) {
		Map<String, Object> requestParams =  new HashMap<String, Object>();

		if(!withParams){
			return requestParams;
		}
		requestParams = getDefaultParams(context);

		if (params != null) {
			requestParams.putAll(params);
		}

		return requestParams;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	protected void showLoading(AsyncPoolLoader loader) {
		if (loader.getLoadingView() != 0) {
			LoaderInfo.add(loader.getLoadingView());
			EventBus.getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.ACTION_START, loader.getLoadingView()));
		}
		loader.showLoading();
	}

	protected void hideLoading(AsyncPoolLoader loader) {
		if (loader.getLoadingView() != 0) {
			LoaderInfo.remove(loader.getLoadingView());
			EventBus.getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.ACTION_FINISH, loader.getLoadingView()));
		}
		loader.hideLoading();
	}

}