package com.cuponera.utils;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.androidquery.AQuery;

public class Utils {

	public static final boolean memCache = true;
	public static final boolean fileCache = true;

	public static String getAppVersion(Context context) {

		String version = "";

		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	/**
	 * if null or non visible characters then returns true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null)
			return true;

		return str.trim().length() == 0;
	}

	/**
	 * if null, non visible characters or "0" then returns true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlankOrZero(String str) {
		return isBlank(str) || "0".equals(str.trim());
	}

	/**
	 * @return Returns screen size
	 * 
	 *         Emerios Supported Values: Android480x854 Android480x800
	 *         Android320x480 Android240x400
	 */
	public static Point getScreenForEmerios(Context ctx) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		Integer width = dm.widthPixels >= 480 ? 480 : dm.widthPixels >= 320 ? 320 : 240;
		Integer height = 0;
		switch (width) {
		case 480:
			height = dm.heightPixels >= 854 ? 854 : 800;
			break;
		case 320:
			height = 480;
			break;
		case 240:
			height = 400;
			break;

		default:
			height = 400;
			break;
		}

		return new Point(width, height);
	}

	private final static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T deserialize(String src, Class<T> type) {
		try {
			return objectMapper.readValue(src, type);
		} catch (Exception e) { // should not fail, the object was already
								// parsed from the service
			e.printStackTrace();
			return null;
		}
	}

	public static String serialize(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (Exception e) { // should not fail, the object was already
								// parsed from the service
			e.printStackTrace();
			return null;
		}
	}

	public static void hideKeyboard(final Context context, final View view) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view == null)
			return;
		inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void showKeyboard(final Context context, final View view) {

		view.requestFocus();
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.showSoftInput(view, 0);
			}
		}, 200);
	}

	public static void loadImageFromUrl(ImageView imageView, String url) {
		AQuery aq = new AQuery(imageView);
		Bitmap bitmap = aq.getCachedImage(url);
		if (bitmap == null) {
			if (Utils.hasInternetConnection(imageView.getContext())) {
				aq.image(url, memCache, fileCache);
			}
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	public static boolean hasInternetConnection(Context context) {
		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	public static String getSCMSHA(Context context) {
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
			Object value = (Object) ai.metaData.get("GIT_SHA");
			return value.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "(unavailable)";
		}

	}

}
