package com.cuponera.prehome;

import android.os.Bundle;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.utils.Utils;

public class PreHomeFragment extends BaseFragment {

	public static PreHomeFragment newInstance() {
		return new PreHomeFragment();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_pre_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		runHome();
		
	}
	public void runHome() {
		if (Utils.hasInternetConnection(getBaseActivity())) {
			getBaseActivity().runHomeActivity();
		}
	}

}
