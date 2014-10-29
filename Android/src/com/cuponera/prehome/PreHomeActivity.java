package com.cuponera.prehome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.event.EventBus;
import com.cuponera.model.Profile;
import com.cuponera.service.profile.CreateProfileRequest;
import com.cuponera.service.profile.ProfileResponse;
import com.cuponera.service.profile.UpdateProfileRequest;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.ValidationUtils;

public class PreHomeActivity extends BaseActivity {

	private PreHomeFragment preHomeFragment;
	private AlertDialog alertDialog;

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getInstance().removeListener(mEventListener);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		navBarFragment.hide();
		navBarFragment.getMenu().disable();

		setPopupsPreferenceAndRunService();
	}

	private void createProfile() {
		CreateProfileRequest request = new CreateProfileRequest(PreHomeActivity.this) {

			@Override
			public void onServiceReturned(ProfileResponse result) {
				super.onServiceReturned(result);
				continueCicle();
			}

			@Override
			public void loadFailed() {
				continueCicle();

			}

		};
		request.execute();
	}

	private void updateProfile() {

		UpdateProfileRequest request = new UpdateProfileRequest(PreHomeActivity.this) {

			@Override
			public void onServiceReturned(ProfileResponse result) {
				super.onServiceReturned(result);
				continueCicle();

			}

			@Override
			public void loadFailed() {
				continueCicle();
			}

		};
		request.setIdProfile(getSettings().getProfile().getProfileID());
		request.execute();

	}

	private void setPopupsPreferenceAndRunService() {
		Profile p = getSettings().getProfile();
		getSettings().setProfile(p);

		if (ValidationUtils.isNullOrEmpty(getSettings().getProfile().getProfileID())) {
			createProfile();
		} else {
			updateProfile();
		}


	}

	protected void showNoInternetConnectionMessage() {
		if (alertDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(ErrorHandler.NO_INTERNET_ERROR);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					System.exit(0);
				}
			});
			alertDialog = builder.create();

			alertDialog.show();
		}
	}
	
	private void continueCicle(){
		preHomeFragment = new PreHomeFragment();
		startFragment(preHomeFragment, false);
	}

}
