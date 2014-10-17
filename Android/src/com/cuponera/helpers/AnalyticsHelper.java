package com.cuponera.helpers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.cuponera.trackers.Tracker;

public class AnalyticsHelper {

	public static final String HOME_SCREEN = "Home-Screen";
	public static final String SHOP_SCREEN = "Shop-Screen";
	public static final String COUPONS_SCREEN = "Coupons-Screen";
	public static final String LOOK_BOOK_SCREEN = "LookBook-Screen";
	public static final String SOCIAL_SCREEN = "Social-Screen";
	public static final String STORES_SCREEN = "Stores-Screen";
	public static final String PROFILE_SCREEN = "Profile-Screen";
	public static final String MAP_SCREEN = "Map-Screen";
	public static final String SETTINGS_SCREEN = "Settings-Screen";

	public static final String CARROUSEL = "Carrousel";
	public static final String STORE_FINDER = "StoreFinderSearch";
	public static final String GPS = "GPS";
	public static final String ZIP_CODE = "ZipCode";
	public static final String CITY_STATE = "City-State";
	public static final String TELEPHONE = "Telephone";
	public static final String SEARCH = "Search";
	public static final String CLEAR = "Clear";
	public static final String STATES = "State";
	public static final String STORE_FINDER_RESULTS = "StoreFinderResults";
	public static final String LIST = "List";
	public static final String STORE = "Store";
	public static final String DETAILS = "Details";
	public static final String STORE_MAP = "Map";
	public static final String STORE_INDICATIONS = "Indications";
	public static final String LOOKBOOK = "Lookbook";
	public static final String LOOKBOOK_OCASSION = "Ocassion";
	public static final String LOOKBOOK_CATEGORY = "Category";
	public static final String LOOKBOOK_COLOR = "Color";
	public static final String LOOKBOOK_STYLE = "Style";
	public static final String LOOKBOOK_RESULTS = "LookbookResults";
	public static final String BUY_NOW = "BuyNow";
	public static final String MY_COUPONS = "MyCoupons";
	public static final String MY_PROFILE = "MyProfile";
	public static final String SUBMIT = "Submit";
	public static final String GENDER = "Gender";
	public static final String TEXT_MESSAGE = "TextMessage";
	public static final String EMAIL = "Email";
	public static final String DIRECT_MAIL = "DirectMail";
	public static final String TERMS_AND_CONDITIONS = "TermsAndConditions";
	public static final String SOCIAL = "Social";
	public static final String FEED = "Feed";

	private static List<Tracker> trackers = new ArrayList<Tracker>();
	private static AnalyticsHelper instance;

	public static AnalyticsHelper getInstance() {
		if (instance == null) {
			instance = new AnalyticsHelper();
		}
		return instance;
	}

	public static void logEvent(String event) {
		for (Tracker t : trackers) {
			t.logEvent(event);
		}
		Log.d("ANALYTICS", event);
	}

	public static void logEventWithCategory(String category, String event) {
		for (Tracker t : trackers) {
			t.logEvent(category, event);
		}
		Log.d("ANALYTICS", category + " " + event);
	}

	public static void logEventWithCategoryAndLabel(String category, String event, String label) {
		for (Tracker t : trackers) {
			t.logEvent(category, event, label);
		}
		Log.d("ANALYTICS", category + " " + event + " " + label);
	}

	public static void logEventWithCategoryAndLabelAndValue(String category, String event, String label, long value) {
		for (Tracker t : trackers) {
			t.logEvent(category, event, label, value);
		}
		Log.d("ANALYTICS", category + " " + event + " " + label + " " + value);
	}

	public static void addTracker(Tracker tracker) {
		if (tracker != null) {
			for (Tracker t : trackers) {
				if (t.getClass().equals(tracker.getClass())) {
					return;
				}
			}
			trackers.add(tracker);
		}
	}

	public static void startTracking(Activity activity) {
		for (Tracker t : trackers) {
			t.startTracking(activity);
		}
	}
}
