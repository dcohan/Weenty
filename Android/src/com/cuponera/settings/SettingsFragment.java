package com.cuponera.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.profile.UpdateProfileRequest;
import com.cuponera.service.state.StateRequest;
import com.cuponera.service.state.StateResponse;

public class SettingsFragment extends BaseFragment {

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

				if (!isChecked) {
					StateRequest stateRequest = new StateRequest(getActivity()) {

						@Override
						public void hideLoading() {
							getBaseActivity().hideLoading();
						}

						@Override
						public void showLoading() {
							getBaseActivity().showLoading();
						}

						@Override
						protected void serviceReady(StateResponse result) {
							if (result != null && result.getState().size() > 0)
								getBaseActivity().dynamicPopup(result.getState());
						}
					};
					if (!stateRequest.isResultCached()) {
						stateRequest.execute();
					}
				}

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
