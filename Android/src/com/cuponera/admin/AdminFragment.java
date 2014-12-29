package com.cuponera.admin;

import com.cuponera.R;
import com.cuponera.service.config.BuildConfig;
import com.cuponera.utils.WebViewWithHeaderFragment;

public class AdminFragment extends WebViewWithHeaderFragment {

	@Override
	protected String getUrl() {
		return BuildConfig.endpoint.getApiProtocol() + "://e-pilar.com.ar";
	}

	@Override
	public String getTitle() {
		return getString(R.string.admin_title);
	}

}
