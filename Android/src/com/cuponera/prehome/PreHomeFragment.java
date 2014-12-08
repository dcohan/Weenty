package com.cuponera.prehome;

import android.os.Bundle;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.settings.Settings;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ValidationUtils;

public class PreHomeFragment extends BaseFragment {

	public static PreHomeFragment newInstance() {
		return new PreHomeFragment();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_pre_home;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ValidationUtils.isNullOrEmpty(Settings.getInstance(getBaseActivity()).getPreHomeImage())) {
			Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.image), Settings.getInstance(getBaseActivity()).getPreHomeImage());
		}
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
