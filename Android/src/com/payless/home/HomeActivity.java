package com.payless.home;

import android.os.Bundle;

import com.payless.BaseActivity;
import com.payless.utils.PushHandler;

public class HomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getData() != null) {
			PushHandler.handleURLOpen(getIntent().getData(), this);
		} else {
			startFragment(new HomeFragment(), false);
		}
	}

}
