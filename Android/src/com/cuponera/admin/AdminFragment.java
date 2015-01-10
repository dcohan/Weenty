package com.cuponera.admin;

import com.cuponera.R;
import com.cuponera.service.config.BuildConfig;
import com.cuponera.utils.WebViewWithHeaderFragment;

public class AdminFragment extends WebViewWithHeaderFragment {

	@Override
	protected String getUrl() {
		return BuildConfig.endpoint.getApiProtocol() + "://weenty.com";
	}

	@Override
	public String getTitle() {
		return getString(R.string.admin_title);
	}

}
