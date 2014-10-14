package com.payless.service.profile;

import android.content.Context;

import com.payless.pool.AsyncPoolLoader.HTTPMethod;

public abstract class CreateProfileRequest extends ProfileRequest {

	public CreateProfileRequest(Context context) {
		super(context);
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPost;
	}

}
