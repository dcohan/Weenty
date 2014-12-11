package com.cuponera;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.cuponera.admin.AdminFragment;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.event.EventListener;
import com.cuponera.helpers.AnalyticsHelper;
import com.cuponera.home.HomeActivity;
import com.cuponera.home.HomeFragment;
import com.cuponera.more.MoreFragment;
import com.cuponera.navigation.MenuFragment.MenuInterface;
import com.cuponera.navigation.NavBarFragment;
import com.cuponera.product.OfferFragment;
import com.cuponera.settings.Settings;
import com.cuponera.settings.SettingsFragment;
import com.cuponera.store.StoreFragment;
import com.cuponera.stores.StoreFinderFragment;
import com.cuponera.utils.Const;
import com.cuponera.utils.LocationServices;
import com.cuponera.utils.WebViewWithHeaderFragment;

public class BaseActivity extends FragmentActivity implements MenuInterface {

	protected NavBarFragment navBarFragment;
	private ProgressDialog loadingDialog;
	private boolean errorDialogPresent;
	private AlertDialog alertDialog;

	@Override
	protected void onStart() {
		super.onStart();
		// GoogleAnalyticsTracker analytics = new GoogleAnalyticsTracker();
		// analytics.startTracking(this);
		// AnalyticsHelper.addTracker(analytics);
		// AnalyticsHelper.startTracking(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		navBarFragment = (NavBarFragment) getSupportFragmentManager().findFragmentById(R.id.navBar);

		navBarFragment.toString();

	}

	@Override
	protected void onResume() {
		super.onResume();
		LocationServices.getInstance(this).startTracking();
		EventBus.getInstance().addListener(mEventListener, ErrorEvent.class);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocationServices.getInstance(this).stopTracking();
		EventBus.getInstance().removeListener(mEventListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		// GoogleAnalyticsTracker analytics = new GoogleAnalyticsTracker();
		// analytics.stopTracking(this);
	}

	public Settings getSettings() {
		return Settings.getInstance(getApplicationContext());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
			toggleMenu();
			return true;
		}
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (navBarFragment.getMenu().isOpen()) {
				navBarFragment.getMenu().close();
				return true;
			} else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void toggleMenu() {
		navBarFragment.getMenu().toggle();
	}

	@Override
	public void onHomeButton() {
		AnalyticsHelper.logEvent(AnalyticsHelper.HOME_SCREEN);
		startFragment(new HomeFragment());
	}

	@Override
	public void onAdminButton() {
		startFragment(new AdminFragment());

	}

	@Override
	public void onSpecialOffers() {
		startFragment(new OfferFragment());

	}

	@Override
	public void onGastronomic() {
		Fragment product = StoreFragment.newInstance(Const.GASTRONOMIC);
		startFragment(product);
	}

	public void openStore(int category) {
		Fragment product = StoreFragment.newInstance(category);
		replaceFragment(product, R.id.container, true);
	}

	@Override
	public void onStoresButton() {
		startFragment(new StoreFinderFragment());

	}

	@Override
	public void onMoreButton() {
		startFragment(new MoreFragment());

	}

	@Override
	public void onPreferenceButton() {
		startFragment(new SettingsFragment());

	}

	public void startFragment(Fragment fragment) {
		startFragment(fragment, true);
	}

	public void cleanBackStack() {
		FragmentManager fm = getSupportFragmentManager();
		int backStackCount = fm.getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {
			int backStackId = fm.getBackStackEntryAt(i).getId();
			fm.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	protected void startFragment(Fragment fragment, boolean animated) {
		cleanBackStack();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.container, fragment);
		if (animated) {
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		transaction.commit();
		navBarFragment.getMenu().close();
	}

	public void pushFragment(Fragment fragment, boolean addToBackStack) {
		replaceFragment(fragment, R.id.container, addToBackStack);
	}

	public void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(containerId, fragment);
		if (addToBackStack) {
			transaction.addToBackStack(fragment.getClass().getName());
		}
		transaction.commit();
		navBarFragment.getMenu().close();
	}

	public void showLoading() {
		if (loadingDialog == null || !loadingDialog.isShowing()) {
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setCancelable(false);
			loadingDialog.setMessage("Cargando...");
			loadingDialog.show();

		}
	}

	public void hideLoading() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
	}

	public void runHomeActivity() {
		Intent intent = new Intent();
		intent = new Intent(BaseActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	public void openURL(final String url, final String title) {

		WebViewWithHeaderFragment webView = new WebViewWithHeaderFragment() {

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			protected String getUrl() {
				return url;
			}
		};

		replaceFragment(webView, R.id.container, true);

	}

	public void openURL(String url) {
		openURL(url, null);
	}

	protected final EventListener<ErrorEvent> mEventListener = new EventListener<ErrorEvent>() {

		@Override
		public void onEvent(final ErrorEvent event) {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					try {
						showDialog(event);

						if (event.isBack()) {
							getSupportFragmentManager().popBackStack();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

		}

	};

	protected void showDialog(ErrorEvent event) {
		Integer messageId = event.getErrorMessages().get(0);
		String message = "";
		if (messageId == R.string.server_error) {
			message = event.getExceptionMessage();
		} else if (messageId == 0) {
			message = event.getExceptionMessage();
		} else {
			message = getResources().getString(messageId);
		}

		boolean mine = false; // to know if this call is the one that
								// switches the errorDialogPresent flag
		if (!errorDialogPresent) {
			errorDialogPresent = true;
			mine = true;

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(message);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					errorDialogPresent = false;
				}
			});
			try {
				alertDialog = builder.create();
				if (!isFinishing()) {
					alertDialog.show();
				}
			} catch (Exception ex) {
				if (mine) {
					errorDialogPresent = false;
				}
			}

		}
	}

	@Override
	public void onChangeState(boolean open) {
		// TODO Auto-generated method stub

	}

}
