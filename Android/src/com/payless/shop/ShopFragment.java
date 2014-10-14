package com.payless.shop;


import com.cuponera.R;
import com.payless.utils.WebViewWithHeaderFragment;

public class ShopFragment extends WebViewWithHeaderFragment {

	@Override
	protected String getUrl() {
		return null;
	}

	@Override
	public String getTitle() {
		return getString(R.string.shop_title);
	}
	
}
