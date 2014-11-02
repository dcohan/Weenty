package com.cuponera.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.service.profile.UpdateProfileRequest;

public class SettingsFragment extends BaseFragment implements HeaderInterface {

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_settings;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewProxy.findToggleButton(R.id.allow_admin).setChecked(Settings.getInstance(getActivity()).AdminAllowed());
		mViewProxy.findToggleButton(R.id.allow_location).setChecked(Settings.getInstance(getActivity()).isLocationEnable());

		mViewProxy.findToggleButton(R.id.allow_admin).setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Settings.getInstance(getActivity()).setAdminAllowed(isChecked);
			}
		});

		mViewProxy.findToggleButton(R.id.allow_location).setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Settings.getInstance(getActivity()).setLocationEnable(isChecked);

				UpdateProfileRequest request = new UpdateProfileRequest(getActivity()) {

					@Override
					public void loadFailed() {
					}
				};
				request.execute();
			}
		});

	}

}
