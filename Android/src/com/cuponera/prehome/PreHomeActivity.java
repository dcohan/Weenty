package com.cuponera.prehome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.event.EventBus;
import com.cuponera.service.prehome.PrehomeRequest;
import com.cuponera.service.prehome.PrehomeResponse;
import com.cuponera.service.profile.CreateProfileRequest;
import com.cuponera.service.profile.ProfileResponse;
import com.cuponera.service.profile.UpdateProfileRequest;
import com.cuponera.settings.Settings;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.LocationServices;
import com.cuponera.utils.LocationServices.RequestLocationListener;
import com.cuponera.utils.ValidationUtils;

public class PreHomeActivity extends BaseActivity {

	private PreHomeFragment preHomeFragment;
	private AlertDialog alertDialog;

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getInstance().removeListener(mEventListener);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		navBarFragment.hide();
		navBarFragment.getMenu().disable();
		FrameLayout fm = (FrameLayout) findViewById(R.id.container);
		fm.setBackgroundDrawable(getResources().getDrawable(R.drawable.prehomepage));

		PrehomeRequest prehomeRequest = new PrehomeRequest(this) {

			@Override
			public void onServiceReturned(PrehomeResponse result) {
				if (result != null && result.getPreHomeImages() != null && result.getPreHomeImages().size() > 0) {
					getSettings().setPrehomeImage(result.getPreHomeImages().get(0).getPrehomeImage());
				}
				continueCicle();
			}

		};
		prehomeRequest.execute();
		getLatitudeAndRun();

	}

	private void createProfile() {
		Settings.getInstance(this).setLocationEnable(true);
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
		request.execute();

	}

	private void getLatitudeAndRun() {
		if (LocationServices.getInstance(this).isLocationEnabled()) {
			LocationServices.getInstance(this).requestAccurateLocation(new RequestLocationListener() {

				@Override
				public void onLocationReceived(Location location) {

					if (location != null) {
						getSettings().setLatitude(location.getLatitude());
						getSettings().setLongitude(location.getLongitude());
					}
					makeProfile();
				}

			});
		} else {
			makeProfile();
		}

	}

	private void makeProfile() {
		if (ValidationUtils.isNullOrEmpty(getSettings().getProfileId())) {
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

	private void continueCicle() {
		preHomeFragment = new PreHomeFragment();
		startFragment(preHomeFragment, false);
	}

}
