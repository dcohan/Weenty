package com.cuponera.service.offer;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.settings.Settings;

@SuppressLint("ParcelCreator")
public class OfferRequest extends AsyncPoolRequest<StoreResponse> implements Parcelable {

	@Override
	public String getPath() {
		return "/GetNearestStoresWithOffers?Latitude=" + Settings.getInstance(context).getLatitude() + "&Longitude="
				+ Settings.getInstance(context).getLongitude();
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
	public void onServiceReturned(StoreResponse result) {
		// TODO Auto-generated method stub

	}

}
