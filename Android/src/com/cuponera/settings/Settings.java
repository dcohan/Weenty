package com.cuponera.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.cuponera.utils.Const;

public class Settings {

	protected final SharedPreferences mSharedPreferences;

	private final Context mContext;
	private static Settings mInstance = null;

	private static final String ACCOUNT_ID = "account_id";
	private static final String PREF_DEVICE_ID = "device_id";
	private static final String LATITUDE = "latitud";
	private static final String LONGITUDE = "longitud";
	private static final String ALLOW_ADMIN = "allow_admin";
	private static final String PROFILE_ID = "profile_id";
	private static final String GEOLOCATION = "geolocation";
	private static final String PREHOMEIMAGE = "prehomeimage";
	private static final String CITY = "city";

	public Settings(Context context) {
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(Const.SETTINGS, Context.MODE_PRIVATE);
	}

	public static Settings getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new Settings(context);
		}

		return mInstance;
	}

	protected SharedPreferences.Editor getEditor() {
		return mSharedPreferences.edit();
	}

	public void setAccountId(String accountId) {
		getEditor().putString(ACCOUNT_ID, accountId).commit();
	}

	public String getAccountId() {
		return mSharedPreferences.getString(ACCOUNT_ID, null);
	}

	public void setCity(String city) {
		getEditor().putString(CITY, city).commit();
	}

	public String getCity() {
		return mSharedPreferences.getString(CITY, null);
	}

	public void setPrehomeImage(String prehomeImage) {
		getEditor().putString(PREHOMEIMAGE, prehomeImage).commit();
	}

	public String getPreHomeImage() {
		return mSharedPreferences.getString(PREHOMEIMAGE, null);
	}

	public void setProfileId(String profileId) {
		getEditor().putString(PROFILE_ID, profileId).commit();
	}

	public String getProfileId() {
		return mSharedPreferences.getString(PROFILE_ID, null);
	}

	public void setLongitude(double longitud) {
		getEditor().putLong(LONGITUDE, Double.doubleToLongBits(longitud)).commit();
	}

	public double getLongitude() {
		return Double.longBitsToDouble(mSharedPreferences.getLong(LONGITUDE, 0));
	}

	public void setLatitude(double latitude) {
		getEditor().putLong(LATITUDE, Double.doubleToLongBits(latitude)).commit();
	}

	public double getLatitude() {
		return Double.longBitsToDouble(mSharedPreferences.getLong(LATITUDE, 0));
	}

	public void setDeviceId(String deviceId) {
		getEditor().putString(PREF_DEVICE_ID, deviceId).commit();
	}

	public String getDeviceId() {
		return mSharedPreferences.getString(PREF_DEVICE_ID, "");
	}

	public boolean AdminAllowed() {
		return mSharedPreferences.getBoolean(ALLOW_ADMIN, false);
	}

	public void setAdminAllowed(boolean adminAllowed) {
		getEditor().putBoolean(ALLOW_ADMIN, adminAllowed).commit();
	}

	public boolean isLocationEnable() {
		return mSharedPreferences.getBoolean(GEOLOCATION, false);
	}

	public void setLocationEnable(boolean locationEnable) {
		getEditor().putBoolean(GEOLOCATION, locationEnable).commit();
	}
}
