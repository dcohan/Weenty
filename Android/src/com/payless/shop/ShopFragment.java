package com.payless.shop;


import com.payless.R;
import com.payless.utils.WebViewWithHeaderFragment;

public class ShopFragment extends WebViewWithHeaderFragment {

	@Override
	protected String getUrl() {
		return BASE_URL;
	}

	@Override
	public String getTitle() {
		return getString(R.string.shop_title);
	}
	
}
