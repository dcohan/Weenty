package com.cuponera.service.profile;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.cuponera.pool.AsyncPoolRequest;
import com.cuponera.settings.Settings;
import com.cuponera.utils.ValidationUtils;

public abstract class ProfileRequest extends AsyncPoolRequest<ProfileResponse> {

	public ProfileRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/profile";
	}

	@Override
	protected Class<?> getResponseClass() {
		return ProfileResponse.class;
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
		params.put("Latitude", Settings.getInstance(context).getLatitude());
		params.put("Longitude", Settings.getInstance(context).getLongitude());
		params.put("Geolocation", Settings.getInstance(context).isLocationEnable());
		if (!ValidationUtils.isNullOrEmpty(Settings.getInstance(context).getProfileId())) {
			params.put("IdProfile", Settings.getInstance(context).getProfileId());
		}

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

	@Override
	public void onServiceReturned(ProfileResponse result) {
		Settings.getInstance(context).setProfileId(result.getProfileID());
		Settings.getInstance(context).setLocationEnable(result.isGeolocation() == 1 ? true : false);
	}

}
