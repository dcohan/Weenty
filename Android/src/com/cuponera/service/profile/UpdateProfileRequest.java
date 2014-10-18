package com.cuponera.service.profile;

import java.util.Map;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;

public abstract class UpdateProfileRequest extends ProfileRequest {

	private double latitude;
	private double longitude;


	public UpdateProfileRequest(Context context) {
		super(context);
	}

	@Override
	public Map<String, Object> getUriParams() {
		Map<String, Object> params = super.getUriParams();
		if (getLatitude() != 0) {
			params.put("Latitude", getLatitude());
		}
		if (getLongitude() != 0) {
			params.put("Longitude", getLongitude());
		}
		return params;
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPut;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}

}
