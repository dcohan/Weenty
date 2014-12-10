package com.cuponera.service.store;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.settings.Settings;

public class StoreRequest extends AsyncPoolRequest<StoreResponse> implements Parcelable {

	private int idCategory;

	@Override
	public String getPath() {
		return "/GetNearestStores?idCategory=" + getIdCategory() + "&Latittud=" + Settings.getInstance(context).getLatitude() + "&Longitude="
				+ Settings.getInstance(context).getLongitude();
	}

	public StoreRequest(Context context) {
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
		return StoreResponse.class;
	}

	@Override
	public void onServiceReturned(StoreResponse result) {

	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

}
