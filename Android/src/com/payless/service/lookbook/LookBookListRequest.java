package com.payless.service.lookbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.payless.pool.AsyncPoolLoader.HTTPMethod;
import com.payless.pool.PaylessAsyncPoolRequest;

public abstract class LookBookListRequest extends PaylessAsyncPoolRequest<LookBookListResponse> {
	
	private List<String> filters;
	private int page = 1;
	
	public LookBookListRequest(Context context) {
		super(context);
	}

	@Override
	public String getPath() {
		return "/LookBook/List";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPost;
	}

	@Override
	protected Class<?> getResponseClass() {
		return LookBookListResponse.class;
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
		list.put("Filters",getFilters());
		return list;
	}

	public List<String> getFilters() {
		if(filters == null){
			filters = new ArrayList<String>();
		}
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
