package com.payless.profile.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cuponera.R;
import com.payless.BaseFragment;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.model.Profile;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.profile.ProfileResponse;
import com.payless.service.profile.UpdateProfileRequest;
import com.payless.settings.Settings;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;

public class SettingsFragment extends BaseFragment implements HeaderInterface {

	private ToggleButton locationCheckbox;
	private ToggleButton pushNotificationsCheckbox;

	@Override
	protected int getLayout() {
		return R.layout.fragment_settings;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		pushNotificationsCheckbox = mViewProxy.findToggleButton(R.id.offers_notifications);
		locationCheckbox = mViewProxy.findToggleButton(R.id.allow_location);

		setSavedValues();
		
		mViewProxy.findButton(R.id.submitButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		});
		
		OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				Profile profile = Settings.getInstance(getBaseActivity()).getProfile();
				
				boolean active = (pushNotificationsCheckbox.isChecked() != profile.isPushNotification()
						|| locationCheckbox.isChecked() != profile.isGeolocation());
				
				mViewProxy.findButton(R.id.submitButton).setEnabled(active);
			}
		};
		
		pushNotificationsCheckbox.setOnCheckedChangeListener(checkedListener);
		locationCheckbox.setOnCheckedChangeListener(checkedListener);

	}

	public void submit() {

		Utils.hideKeyboard(getBaseActivity(), mViewProxy.getView());

		UpdateProfileRequest request = new UpdateProfileRequest(getBaseActivity()) {

			@Override
			public void loadFailed() {

			}

			@Override
			public void onServiceReturned(ProfileResponse result) {
				super.onServiceReturned(result);
				if (result.succes()) {
					Toast.makeText(context, getString(R.string.settings_thanks), Toast.LENGTH_LONG).show();
					mViewProxy.findButton(R.id.submitButton).setEnabled(false);
				} else {
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.SYSTEM_SERVER_ERROR));
				}
			}

			@Override
			public void hideLoading() {
				getBaseActivity().hideLoading();
			}

			@Override
			public void showLoading() {
				getBaseActivity().showLoading();
			}
		};
		
		request.setPushNotification(pushNotificationsCheckbox.isChecked());
		request.setGeolocation(locationCheckbox.isChecked());

		request.execute();

	}

	@Override
	public String getTitle() {
		return getString(R.string.settings_title);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	private void setSavedValues() {
		Profile profile = Settings.getInstance(getBaseActivity()).getProfile();

		pushNotificationsCheckbox.setChecked(profile.isPushNotification());
		locationCheckbox.setChecked(profile.isGeolocation());

	}

}
