package com.cuponera.service.category;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.pool.AsyncPoolRequest;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public abstract class CategoryRequest extends AsyncPoolRequest<CategoryResponse> implements Parcelable {

	private static Cache<Object, Object> categoryCache;

	static {
		initCache();
	}

	private static void initCache() {
		if (categoryCache == null) {
			categoryCache = CacheBuilder.newBuilder().maximumSize(500).expireAfterAccess(5, TimeUnit.MINUTES).build();
		}
	}

	public static void clearCaches() {

		if (categoryCache != null) {
			categoryCache.invalidateAll();
			categoryCache.cleanUp();
		}
	}

	public CategoryRequest(Context context) {
		super(context);
		initCache();
	}

	public boolean isResultCached() {
		String uriHash = "/category";
		CategoryResponse response = (CategoryResponse) categoryCache.getIfPresent(uriHash);

		if (response != null) {
			onServiceReturned(response);
		}

		return response != null;
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

	private boolean emptyResponse(CategoryResponse response) {
		if (response == null)
			return true;

		if (response.getCategory() == null || response.getCategory().isEmpty())
			return true;

		return false;
	}

	@Override
	public void onServiceReturned(CategoryResponse result) {
		String uriHash = "/category";
		if (!emptyResponse(result)) {
			categoryCache.put(uriHash, result);
		}

		serviceReady(result);

	}

	protected abstract void serviceReady(CategoryResponse response);

}
