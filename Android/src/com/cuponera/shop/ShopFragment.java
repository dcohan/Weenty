package com.cuponera.shop;


import com.cuponera.R;
import com.cuponera.utils.WebViewWithHeaderFragment;

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
