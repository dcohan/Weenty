package com.cuponera.prehome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.event.EventBus;
import com.cuponera.model.Profile;
import com.cuponera.service.profile.CreateProfileRequest;
import com.cuponera.service.profile.ProfileResponse;
import com.cuponera.service.profile.UpdateProfileRequest;
import com.cuponera.utils.ErrorHandler;

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

		// StartAppRequest request = new StartAppRequest(PreHomeActivity.this) {
		//
		// @Override
		// public void onServiceReturned(StartAppResponse result) {
		//
		// if (result.succes()) {
		// // For test
		// // getSettings().setStartAppDigest(null);
		//
		// if (result.getDigest() != null &&
		// !result.getDigest().equals(getSettings().getStartAppDigest())) {
		// getSettings().setStartAppDigest(result.getDigest());
		//
		// getSettings().setPrehomeInfo(result.getPreHome());
		//
		// getSettings().setHomeOffers(result.getHomeOffers());
		//
		// getSettings().setCoupons(result.getCoupons());
		//
		// getSettings().setLookBookFilters(result.getLookBookFilters());
		// if (result.getProfile() != null) {
		// getSettings().setProfile(result.getProfile());
		// }
		//
		// }
		// }
		// askNotifications();
		// }
		//
		// @Override
		// public void loadWasCancelled() {
		// super.loadWasCancelled();
		// preHomeFragment.reload();
		// }
		//
		// @Override
		// public void loadFailed() {
		// if (Utils.hasInternetConnection(PreHomeActivity.this)) {
		// preHomeFragment.reload();
		// } else {
		// showNoInternetConnectionMessage();
		// }
		// }
		//
		// };
		//
		// request.execute();

	}

	private void createProfile() {
		CreateProfileRequest request = new CreateProfileRequest(PreHomeActivity.this) {

			@Override
			public void onServiceReturned(ProfileResponse result) {
				preHomeFragment.reload();
			}

			@Override
			public void loadFailed() {
				Log.d("PreHomeActivity", "CreateProfile Failed");

			}

		};
		request.execute();
	}

	private void updateProfile() {

		UpdateProfileRequest request = new UpdateProfileRequest(PreHomeActivity.this) {

			@Override
			public void onServiceReturned(ProfileResponse result) {
				super.onServiceReturned(result);
				preHomeFragment.reload();
			}

			@Override
			public void loadFailed() {
				Log.d("PreHomeActivity", "UpdateProfile Failed");
			}

		};
		request.execute();

	}

	private void setPopupsPreferenceAndRunService() {
		Profile p = getSettings().getProfile();
		getSettings().setProfile(p);

		if (getSettings().getProfile().isFirstRun()) {
			createProfile();
		} else {
			updateProfile();
		}
		preHomeFragment = new PreHomeFragment();
		startFragment(preHomeFragment, false);

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

}
