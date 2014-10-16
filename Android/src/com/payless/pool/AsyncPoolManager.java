package com.payless.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.payless.BaseActivity;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.event.LoaderEvent;
import com.payless.service.HttpService;
import com.payless.service.ServiceConfig;
import com.payless.ui.loader.LoaderInfo;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;
import com.payless.utils.ValidationUtils;

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
		if (mInstance == null
				|| mInstance.getContext() != context) {
			mInstance = new AsyncPoolManager(context);
		}

		return mInstance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void executeLoaderWith(BaseAsyncPoolRequest request) {

		Map<String, Object> uriParams = getDefaultParamsAppendingList(request.getUriParams(), request.getContext());

		AsyncPoolLoader loader = new AsyncPoolLoader(getContext(), request.getHttpMethod(), uriParams, request.getProtocol(),
				request.getHost(), request.getPath());

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
			if(result.equals("no_internet")){
				throw new Exception();
			}
			loader.getCallback().processResponse(result);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throwErrorMessage = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(!Utils.hasInternetConnection(context)) {
				
				throwErrorMessage = true;
				callbackHandledError = true;
				EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.NO_INTERNET_ERROR));
				
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
	
				if (result.equals(HttpService.SSLEXEPTION)) {
					showSSLErrorPopUp(loader.getContext());
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
		EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.SYSTEM_SERVER_ERROR));
	}

	private void showSSLErrorPopUp(Context context) {
		// cancel pending service loaders
		for (AsyncPoolLoader loader : loaders) {
			loader.cancel();
			hideLoading(loader);
		}

		loaders.removeAll(loaders);

		try {
			((BaseActivity) context).throwSSLErrorMessage();
		} catch (Exception e) {
			Log.e("Async pool manager", "Context is not a common base activity, ssl not thrown");
			throwErrorMessage = false;
			showServerErrorPopUp();
		}
	}

	private Map<String, Object> getDefaultParams(Context context) {
		Map<String, Object> defaultParams = new HashMap<String, Object>();
		
		defaultParams.put("DeviceId", ServiceConfig.getDeviceId());
		defaultParams.put("IdDeviceOs", ServiceConfig.DEVICE_TYPE);
		defaultParams.put("IdDeviceType", ServiceConfig.getAppType(context));
		defaultParams.put("AppVersion", Utils.getAppVersion(context));
		defaultParams.put("ResolutionWidth", Utils.getScreenForEmerios(context).x);
		defaultParams.put("ResolutionHeight", Utils.getScreenForEmerios(context).y);

		return defaultParams;
	}

	private Map<String, Object> getDefaultParamsAppendingList(Map<String, Object> params, Context context) {

		Map<String, Object> requestParams = getDefaultParams(context);

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
	
	protected void hideLoading(AsyncPoolLoader loader){
		if (loader.getLoadingView() != 0) {
			LoaderInfo.remove(loader.getLoadingView());  
			EventBus.getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.ACTION_FINISH, loader.getLoadingView()));
		} 
		loader.hideLoading();
	}

}