package com.payless.service.profile;

import com.payless.pool.AsyncPoolLoader.HTTPMethod;

import android.content.Context;

public abstract class CreateProfileRequest extends ProfileRequest {

	public CreateProfileRequest(Context context) {
		super(context);
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPost;
	}

}
