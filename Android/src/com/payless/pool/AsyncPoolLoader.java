package com.payless.pool;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.payless.service.HttpService;

public class AsyncPoolLoader implements LoaderCallbacks<String> {

	public static enum HTTPMethod {
		HTTPGet,
		HTTPPost,
		HTTPPut
	}

	private ServiceCallback callback;

	public interface ServiceCallback {
		/**
		 * This method is called to process the response in the request.
		 * 
		 * @param response
		 *            - the service string response
		 * @throws Exception
		 */
		public void processResponse(String response) throws Exception;

		/**
		 * This method should be used by child classes in case you want them to
		 * do something special when the system throws a time out exception.
		 * Should return true if the callback is responsible for handling this
		 * error and the pool manager should not interfere. Returns false by
		 * default, and to allow the pool manager to manage this error. Also
		 * this is called when the internet was lost in the middle of the
		 * request
		 * 
		 * @return - True if callback consumed, false otherwise
		 */
		public boolean isServerTimeOutError();

		/**
		 * This method should be used by child classes in case you want them to
		 * do something special when the system throws a regular exception.
		 * Should return true if the callback is responsible for handling this
		 * error and the pool manager should not interfere. Returns false by
		 * default, and to allow the pool manager to manage this error
		 * 
		 * @return - True if callback consumed, false otherwise
		 */
		public boolean isRegularError();

		/**
		 * This method is called when the load was cancelled, in case the
		 * manager has required so
		 */
		public void loadWasCancelled();
		

		public void loadFailed();

		public int getLoadingView();
		
		public void showLoading();
		
		public void hideLoading();
		
	}

	private Context context;
	private Map<String, Object> uriParams;
	private HTTPMethod httpMethod;
	private String protocol;
	private String host;
	private String path;

	private String serviceResponse;
	private AsyncTaskLoader<String> loader;
	private boolean cancelRequested = false;

	private int loadingView;

	public AsyncPoolLoader(Context context, HTTPMethod httpMethod, Map<String, Object> uriParams, String protocol,
			String host, String path) {
		this.context = context;
		this.uriParams = uriParams;
		this.httpMethod = httpMethod;
		this.protocol = protocol;
		this.host = host;
		this.path = path;
	}

	/**
	 * @return the callback
	 */
	public ServiceCallback getCallback() {
		return callback;
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(ServiceCallback callback) {
		this.callback = callback;
	}

	@Override
	public Loader<String> onCreateLoader(int arg0, Bundle arg1) {
		AsyncTaskLoader<String> loader = new AsyncTaskLoader<String>(context) {
			@Override
			public String loadInBackground() {
				serviceResponse = "";

				switch (httpMethod) {
				case HTTPGet:
					serviceResponse = new HttpService().poolGet(protocol, host, path, uriParams);
					break;
				case HTTPPost:
					serviceResponse = new HttpService().poolPost(protocol, host, path, uriParams);
					break;
				case HTTPPut:
					serviceResponse = new HttpService().poolPut(protocol, host, path, uriParams);
					break;
				}

				return serviceResponse;
			}

		};

		loader.forceLoad();
		return loader;
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onLoadFinished(Loader<String> loader, String result) {
		serviceResponse = result;

		Handler resultHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				AsyncPoolManager.getInstance(context).loaderReturns(serviceResponse, AsyncPoolLoader.this);
			}
		};

		if (!cancelRequested) {
			resultHandler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onLoaderReset(Loader<String> arg0) {
	}

	public void cancel() {
		loader.cancelLoad();
		callback.loadWasCancelled();
		cancelRequested = true;
	}

	/**
	 * @return the loadingView
	 */
	public int getLoadingView() {
		return loadingView;
	}

	/**
	 * @param loadingView
	 *            the loadingView to set
	 */
	public void setLoadingView(int loadingView) {
		this.loadingView = loadingView;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}
	
	public void showLoading() {
		if(callback != null) {
			callback.showLoading();
		}
	}
	
	public void hideLoading() {
		if(callback != null) {
			callback.hideLoading();
		}
	}
	
}