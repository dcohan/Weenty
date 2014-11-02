package com.cuponera.pool;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolLoader.ServiceCallback;
import com.cuponera.service.BaseResponse;

public abstract class BaseAsyncPoolRequest<ResponseClass extends BaseResponse> implements ServiceCallback {
	protected Context context;

	protected int loadingView;

	public BaseAsyncPoolRequest(Context context) {
		this.context = context;
	}

	public abstract String getHost();

	public abstract String getPath();

	public abstract String getProtocol();

	public abstract HTTPMethod getHttpMethod();

	public Context getContext() {
		return context;
	}

	public abstract Map<String, Object> getUriParams();

	@SuppressWarnings("unchecked")
	@Override
	public void processResponse(String httpResponse) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US));
		BaseResponse baseResponse = new BaseResponse();
		baseResponse = (BaseResponse) mapper.readValue(httpResponse, BaseResponse.class);

		ResponseClass responseClass = (ResponseClass) getResponseClass().newInstance();

		JSONObject responseJson = new JSONObject(new JSONTokener(httpResponse));
		responseClass = (ResponseClass) mapper.readValue(responseJson.toString(), getResponseClass());
		responseClass.setData(responseJson);
		responseClass.setResult(baseResponse.getResult());
		responseClass.setErrors(baseResponse.getErrors());

		onServiceReturned(responseClass);
	}

	public abstract void onServiceReturned(ResponseClass result);

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

	public void execute() {
		AsyncPoolManager.getInstance(getContext()).executeLoaderWith(this);
	}

	protected abstract Class<?> getResponseClass();

}