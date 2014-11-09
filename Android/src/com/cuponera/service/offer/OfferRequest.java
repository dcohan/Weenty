package com.cuponera.service.offer;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;

public class OfferRequest extends AsyncPoolRequest<OfferResponse> implements Parcelable {

	@Override
	public String getPath() {
		return "/offer?$expand=product/company";
	}

	public OfferRequest(Context context) {
		super(context);
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
	public void loadFailed() {
	}

	@Override
	public void showLoading() {
		getBaseActivity().showLoading();
	}

	@Override
	public void hideLoading() {
		getBaseActivity().hideLoading();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	public Map<String, Object> getUriParams() {
		Map<String, Object> defaultParams = new HashMap<String, Object>();
		return defaultParams;
	}

	@Override
	protected Class<?> getResponseClass() {
		return OfferResponse.class;
	}

	@Override
	public void onServiceReturned(OfferResponse result) {

	}

}
