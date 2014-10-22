package com.cuponera.pool;

import android.content.Context;

import com.cuponera.BaseActivity;
import com.cuponera.service.BaseResponse;
import com.cuponera.service.ServiceConfig;

public abstract class AsyncPoolRequest<ResponseClass extends BaseResponse> extends BaseAsyncPoolRequest<ResponseClass> {

	public AsyncPoolRequest(Context context) {
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
