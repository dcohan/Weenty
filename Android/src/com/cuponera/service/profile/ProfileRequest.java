package com.cuponera.service.profile;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.cuponera.pool.PaylessAsyncPoolRequest;
import com.cuponera.settings.Settings;
import com.cuponera.utils.ValidationUtils;

public abstract class ProfileRequest extends PaylessAsyncPoolRequest<ProfileResponse> {

	private String idProfile;

	public ProfileRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/Profile";
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

		if (!ValidationUtils.isNullOrEmpty(getIdProfile())) {
			params.put("IdProfile", getIdProfile());
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


	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	@Override
	public void onServiceReturned(ProfileResponse result) {
		if (result.succes()) {
			Settings.getInstance(context).setProfile(result.getProfile());
		}
	}

}
