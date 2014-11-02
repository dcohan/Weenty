package com.cuponera.service.category;

import java.util.Map;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;

public class CategoryRequest extends AsyncPoolRequest<CategoryResponse> implements Parcelable {

	public CategoryRequest(Context context) {
		super(context);
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
	public void loadFailed() {
	}

	@Override
	public void showLoading() {
		getBaseActivity().showLoading();
	}

	@Override
	public void hideLoading() {
		getBaseActivity().hideLoading();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	@Override
	public String getPath() {
		return "/category";
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPGet;
	}

	@Override
	public Map<String, Object> getUriParams() {
		return null;
	}


	@Override
	protected Class<?> getResponseClass() {
		return CategoryResponse.class;
	}

	@Override
	public void onServiceReturned(CategoryResponse result) {
		// TODO Auto-generated method stub
		
	}

}
