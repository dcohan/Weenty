package com.payless.pool;

import android.content.Context;

import com.payless.BaseActivity;
import com.payless.service.BaseResponse;
import com.payless.service.ServiceConfig;

public abstract class PaylessAsyncPoolRequest<ResponseClass extends BaseResponse> extends BaseAsyncPoolRequest<ResponseClass> {

	public PaylessAsyncPoolRequest(Context context) {
		super(context);
	}
	
	@Override
	public String getHost() {
		return ServiceConfig.API_HOST;
	}
	
	@Override
	public String getProtocol() {
		return ServiceConfig.API_PROTOCOL;
	}
	
	protected BaseActivity getBaseActivity(){
		return ((BaseActivity) getContext());
	}
	
}
