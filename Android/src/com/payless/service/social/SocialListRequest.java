package com.payless.service.social;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.payless.pool.AsyncPoolLoader.HTTPMethod;
import com.payless.pool.PaylessAsyncPoolRequest;

public abstract class SocialListRequest extends PaylessAsyncPoolRequest<SocialListResponse> {
	
	private int page = 1;
	
	public SocialListRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/Social/List";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	protected Class<?> getResponseClass() {
		return SocialListResponse.class;
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
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("Page",getPage());
		return list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
