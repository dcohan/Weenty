package com.cuponera.service.profile;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.settings.Settings;

public abstract class UpdateProfileRequest extends ProfileRequest {

	public UpdateProfileRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/profile(" + Settings.getInstance(context).getProfileId() + ")";
	}
	
	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPut;
	}

}
