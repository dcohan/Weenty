package com.cuponera.home;

import android.os.Bundle;

import com.cuponera.BaseActivity;

public class HomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startFragment(new HomeFragment(), false);
	}

}
