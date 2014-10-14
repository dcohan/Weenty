package com.payless.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;

import com.payless.BaseActivity;
import com.payless.R;
import com.payless.coupons.MyCouponsFragment;
import com.payless.home.HomeFragment;
import com.payless.lookbook.LookBookFragment;
import com.payless.profile.ProfileFragment;
import com.payless.service.ServiceConfig;
import com.payless.settings.Settings;
import com.payless.shop.ShopFragment;
import com.payless.social.SocialFragment;
import com.payless.stores.StoreFinderFragment;

public class PushHandler {

	public static final String HOME = "home";
	public static final String SHOP = "shop";
	public static final String COUPONS = "coupons";
	public static final String LOOKBOOK = "lookbook";
	public static final String SOCIAL = "social";
	public static final String STORES = "stores";
	public static final String PROFILE = "profile";
	public static final String WEB_VIEW = "webview";
	public static final String DEVICE_INFO = "deviceInfo";

	public static void handleURLOpen(Uri uri, BaseActivity activity) {
		if (uri != null) {
			if (uri.toString().startsWith("payless://")) {
				redirectToAppModule(uri, activity);
			} else {
				activity.startFragment(new HomeFragment());
			}
		}
	}

	private static void redirectToAppModule(Uri moduleToRedirect, BaseActivity activity) {

		String module = moduleToRedirect.getHost();
		if (module.equalsIgnoreCase(HOME)) {
			activity.startFragment(new HomeFragment());
		} else if (module.equalsIgnoreCase(SHOP)) {
			activity.startFragment(new ShopFragment());
		} else if (module.equalsIgnoreCase(COUPONS)) {
			if (moduleToRedirect.getPath() != "") {
				activity.startFragment(MyCouponsFragment.newInstance(moduleToRedirect.getPath().substring(1)));
			} else {
				activity.startFragment(new MyCouponsFragment());
			}
		} else if (module.equalsIgnoreCase(LOOKBOOK)) {
			activity.startFragment(new LookBookFragment());
		} else if (module.equalsIgnoreCase(SOCIAL)) {
			activity.startFragment(new SocialFragment());
		} else if (module.equalsIgnoreCase(STORES)) {
			activity.startFragment(new StoreFinderFragment());
		} else if (module.equalsIgnoreCase(PROFILE)) {
			activity.startFragment(new ProfileFragment());
		} else if (module.equalsIgnoreCase(WEB_VIEW) && moduleToRedirect.getPath() != "") {
			activity.openURL(moduleToRedirect.getPath().substring(1));
		} else if (module.equalsIgnoreCase(DEVICE_INFO)) {
			showDeviceInfo(activity);
		} else
			activity.startFragment(new HomeFragment());
	}

	private static void showDeviceInfo(final Activity activity) {
		String title = "Payless";
		String message = String.format("DeviceID %s \n Profile ID: %s \n XID: %s \n App version: %s \n Env: %s \n SHA: %s \n",
				ServiceConfig.getDeviceId(), Settings.getInstance(activity).getProfile().getProfileID(), Settings.getInstance(activity).getProfile()
						.getXID(), Utils.getAppVersion(activity), ServiceConfig.API_HOST, Utils.getSCMSHA(activity));

		Builder builder = new AlertDialog.Builder(activity);

		if (title != null) {
			builder.setTitle(title);
		}

		builder.setMessage(message);

		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		builder.create();
		builder.show();
	}

}
