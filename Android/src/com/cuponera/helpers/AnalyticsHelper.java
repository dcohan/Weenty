package com.cuponera.helpers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.cuponera.trackers.Tracker;

public class AnalyticsHelper {


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
		//	t.logEvent(event);
		}
		Log.d("ANALYTICS", event);
	}

	public static void logEventWithCategory(String category, String event) {
		for (Tracker t : trackers) {
			//t.logEvent(category, event);
		}
		Log.d("ANALYTICS", category + " " + event);
	}

	public static void logEventWithCategoryAndLabel(String category, String event, String label) {
		for (Tracker t : trackers) {
		//	t.logEvent(category, event, label);
		}
		Log.d("ANALYTICS", category + " " + event + " " + label);
	}

	public static void logEventWithCategoryAndLabelAndValue(String category, String event, String label, long value) {
		for (Tracker t : trackers) {
			//t.logEvent(category, event, label, value);
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
