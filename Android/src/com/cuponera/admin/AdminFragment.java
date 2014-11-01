package com.cuponera.admin;


import com.cuponera.R;
import com.cuponera.utils.WebViewWithHeaderFragment;

public class AdminFragment extends WebViewWithHeaderFragment {

	@Override
	protected String getUrl() {
		return "http://192.168.1.40:2537";
	}

	@Override
	public String getTitle() {
		return getString(R.string.admin_title);
	}
	
}
