package com.payless.service.stores;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.payless.pool.AsyncPoolLoader.HTTPMethod;
import com.payless.pool.PaylessAsyncPoolRequest;

@SuppressLint("ParcelCreator")
public abstract class StoresRequest extends PaylessAsyncPoolRequest<StoresResponse> implements Parcelable {

	private Double latitude;
	private Double longitud;
	private String cityName;
	private String zipCode;
	private String state;
	private int page = 1;

	public StoresRequest(Context context) {
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
	public String getPath() {
		return "/Stores/Search";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPost;
	}

	@Override
	public Map<String, Object> getUriParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Latitude", getLatitude());
		params.put("Longitude", getLongitud());
		params.put("ZipCode", getZipCode());
		params.put("City", getCityName());
		params.put("State", getState());
		params.put("Page", getPage());
		return params;
	}

	public void setUriParams(Map<String, Object> uriParams) {

	}

	@Override
	protected Class<?> getResponseClass() {
		return StoresResponse.class;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int getLoadingView() {
		return 0;
	}

	@Override
	public void showLoading() {
		getBaseActivity().showLoading();
	}

	@Override
	public void hideLoading() {
		getBaseActivity().hideLoading();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

}
