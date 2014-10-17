package com.cuponera.service.profile;

import android.content.Context;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;

public abstract class CreateProfileRequest extends ProfileRequest {

	public CreateProfileRequest(Context context) {
		super(context);
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPost;
	}

}
