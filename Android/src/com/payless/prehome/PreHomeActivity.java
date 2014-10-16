package com.payless.prehome;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.payless.BaseActivity;
import com.payless.R;
import com.payless.event.EventBus;
import com.payless.model.Profile;
import com.payless.service.profile.CreateProfileRequest;
import com.payless.service.profile.ProfileResponse;
import com.payless.service.profile.UpdateProfileRequest;
import com.payless.service.startapp.StartAppRequest;
import com.payless.service.startapp.StartAppResponse;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;
import com.payless.utils.ValidationUtils;
import com.xtify.sdk.api.NotificationsPreference;
import com.xtify.sdk.api.XtifyLocation;
import com.xtify.sdk.api.XtifySDK;

public class PreHomeActivity extends BaseActivity {

	private PreHomeFragment preHomeFragment;
	private AlertDialog alertDialog;
	private AlertDialog askDialog;
	private boolean pushEnabled;
	private boolean locationEnabled;

	@Override
	protected void onStart() {
		super.onStart();
		XtifyLocation.enableRepetitiveLocUpdate(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getInstance().removeListener(mEventListener); // Pre home has
																// no event
																// listener.
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		navBarFragment.hide();
		navBarFragment.getMenu().disable();

		preHomeFragment = new PreHomeFragment();
		startFragment(preHomeFragment, false);
		
		NotificationsPreference.setSoundEnabled(getApplicationContext(), true);
		NotificationsPreference.setIcon(getApplicationContext(), R.drawable.icon);

		StartAppRequest request = new StartAppRequest(PreHomeActivity.this) {

			@Override
			public void onServiceReturned(StartAppResponse result) {

				if (result.succes()) {
					// For test
//					getSettings().setStartAppDigest(null);

					if (result.getDigest() != null && !result.getDigest().equals(getSettings().getStartAppDigest())) {
						getSettings().setStartAppDigest(result.getDigest());

						getSettings().setPrehomeInfo(result.getPreHome());

						getSettings().setHomeOffers(result.getHomeOffers());

						getSettings().setCoupons(result.getCoupons());

						getSettings().setLookBookFilters(result.getLookBookFilters());
						if (result.getProfile() != null) {
							getSettings().setProfile(result.getProfile());
						}

					}
				}
				if (ValidationUtils.isNullOrEmpty(getSettings().getProfile().getProfileID()) || getSettings().getProfile().isFirstRun()) {
					initXtify();
					askNotifications();
				} else if (ValidationUtils.isNullOrEmpty(getSettings().getProfile().getXID())) {
					initXtify();
					if (!ValidationUtils.isNullOrEmpty(XtifySDK.getXidKey(PreHomeActivity.this))) {
						updateProfile();
					} else {
						preHomeFragment.reload();
					}
				} else {
					preHomeFragment.reload();
				}
			}

			@Override
			public void loadWasCancelled() {
				super.loadWasCancelled();
				preHomeFragment.reload();
			}

			@Override
			public void loadFailed() {
				if (Utils.hasInternetConnection(PreHomeActivity.this)) {
					preHomeFragment.reload();
				} else {
					showNoInternetConnectionMessage();
				}
			}

		};

		request.execute();

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
		String xid = XtifySDK.getXidKey(this);
		if (!ValidationUtils.isNullOrEmpty(xid)) {
			request.setXid(xid);
		}
		request.execute();
	}

	private void initXtify() {
		try {
			XtifySDK.start(getApplicationContext(), getString(R.string.xtify_app_key), getString(R.string.xtify_sender_id));
		} catch (Exception e) {
			Log.d("PreHomeActivity", "Xtify Failed");
		}
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
		request.setXid(XtifySDK.getXidKey(this));
		request.execute();

	}

	private void askNotifications() {

		Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		builder.setIcon(android.R.drawable.ic_dialog_alert).setMessage(getString(R.string.enable_push_notifications))
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						pushEnabled = true;
						askLBS();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						pushEnabled = false;
						askLBS();
					}
				});

		askDialog = builder.create();
		askDialog.show();
	}

	private void askLBS() {

		Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);

		builder.setIcon(android.R.drawable.ic_dialog_alert).setMessage(getString(R.string.enable_location_service))
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						locationEnabled = true;
						setPopupsPreferenceAndRunService();
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						locationEnabled = false;
						setPopupsPreferenceAndRunService();
					}
				});

		askDialog = builder.create();
		askDialog.show();

	}

	private void setPopupsPreferenceAndRunService() {
		Profile p = getSettings().getProfile();
		p.setPushNotification(pushEnabled);
		p.setGeolocation(locationEnabled);
		getSettings().setProfile(p);

		if (getSettings().getProfile().isFirstRun()) {
			updateProfile();
		} else {
			createProfile();
		}

	}

	protected void showNoInternetConnectionMessage() {
		if (alertDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(PaylessErrorHandler.NO_INTERNET_ERROR);
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
