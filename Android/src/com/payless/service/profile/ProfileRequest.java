package com.payless.service.profile;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.payless.model.Profile;
import com.payless.pool.PaylessAsyncPoolRequest;
import com.payless.settings.Settings;
import com.payless.utils.ValidationUtils;

public abstract class ProfileRequest extends PaylessAsyncPoolRequest<ProfileResponse> {

	private String idProfile;
	private String xid;
	private boolean geolocation;
	private boolean pushNotification;

	public ProfileRequest(Context context) {
		super(context);
		Profile profile = Settings.getInstance(context).getProfile();
		if (profile != null) {
			setGeolocation(profile.isGeolocation());
			setPushNotification(profile.isPushNotification());
		}
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

		if (!ValidationUtils.isNullOrEmpty(getXid())) {
			params.put("Xid", getXid());
		}
		if (!ValidationUtils.isNullOrEmpty(getIdProfile())) {
			params.put("IdProfile", getIdProfile());
		}
		params.put("Geolocation", isGeolocation());
		params.put("PushNotification", isPushNotification());

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

	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public boolean isGeolocation() {
		return geolocation;
	}

	public void setGeolocation(boolean geolocation) {
		this.geolocation = geolocation;
	}

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(boolean pushNotification) {
		this.pushNotification = pushNotification;
	}

	@Override
	public void onServiceReturned(ProfileResponse result) {
		if (result.succes()) {
			Settings.getInstance(context).setProfile(result.getProfile());
		}
	}

}
