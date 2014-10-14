package com.payless.utils;

import android.os.Bundle;
import android.view.View;

import com.cuponera.R;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;

public abstract class WebViewWithHeaderFragment extends WebViewFragment implements HeaderInterface {
	
	@Override
	protected int getLayout() {
		return R.layout.fragment_web_view_with_header;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}
	
	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}
	
}
