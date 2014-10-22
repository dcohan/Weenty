package com.cuponera.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.cuponera.model.Coupons;
import com.cuponera.model.HomeOffers;
import com.cuponera.model.LookBookFilters;
import com.cuponera.model.PreHome;
import com.cuponera.model.Profile;
import com.cuponera.utils.Const;
import com.cuponera.utils.Utils;

public class Settings {

	protected final SharedPreferences mSharedPreferences;

	private final Context mContext;
	private static Settings mInstance = null;

	private static final String ACCOUNT_ID = "account_id";
	private static final String PREF_DEVICE_ID = "device_id";
	private static final String START_APP_DIGEST = "start_app_digest";
	private static final String PREHOME_INFO = "prehome_info";
	private static final String HOME_OFFERS = "home_offers";
	private static final String COUPONS = "coupons";
	private static final String LOOK_BOOK_FILTERS = "look_book_filters";
	private static final String PROFILE = "profile";

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

	public void setDeviceId(String deviceId) {
		getEditor().putString(PREF_DEVICE_ID, deviceId).commit();
	}

	public String getDeviceId() {
		return mSharedPreferences.getString(PREF_DEVICE_ID, "");
	}

	public void setStartAppDigest(String digest) {
		getEditor().putString(START_APP_DIGEST, digest).commit();
	}

	public String getStartAppDigest() {
		return mSharedPreferences.getString(START_APP_DIGEST, "");
	}

	public void setPrehomeInfo(PreHome preHome) {
		getEditor().putString(PREHOME_INFO, preHome.serialize()).commit();
	}

	public PreHome getPreHomeInfo() {
		String string = mSharedPreferences.getString(PREHOME_INFO, "");
		return Utils.deserialize(string, PreHome.class);
	}

	public void setHomeOffers(HomeOffers homeOffers) {
		getEditor().putString(HOME_OFFERS, homeOffers.serialize()).commit();
	}

	public HomeOffers getHomeOffers() {
		String string = mSharedPreferences.getString(HOME_OFFERS, "");
		return Utils.deserialize(string, HomeOffers.class);
	}

	public void setCoupons(Coupons coupons) {
		getEditor().putString(COUPONS, coupons.serialize()).commit();
	}

	public Coupons getCoupons() {
		String string = mSharedPreferences.getString(COUPONS, "");
		return Utils.deserialize(string, Coupons.class);
	}

	public void setLookBookFilters(LookBookFilters filters) {
		getEditor().putString(LOOK_BOOK_FILTERS, filters.serialize()).commit();
	}

	public LookBookFilters getLookBookFilters() {
		String string = mSharedPreferences.getString(LOOK_BOOK_FILTERS, "");
		return Utils.deserialize(string, LookBookFilters.class);
	}

	public void setProfile(Profile profile) {
		String profileString = "";
		if(profile != null){
			profileString = profile.serialize();
		}
		getEditor().putString(PROFILE, profileString).commit();
	}

	public Profile getProfile() {
		String string = mSharedPreferences.getString(PROFILE, "");
		Profile profile = Utils.deserialize(string, Profile.class);
		if (profile == null) {
			profile = new Profile();
		}
		return profile;
	}
}
