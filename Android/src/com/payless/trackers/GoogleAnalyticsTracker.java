package com.payless.trackers;

import android.app.Activity;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class GoogleAnalyticsTracker implements Tracker {

	private static final String SCREEN_IDENTIFIER = "-Screen";
	private EasyTracker easyTracker;

	@Override
	public void startTracking(Activity activity) {
		EasyTracker.getInstance(activity).activityStart(activity);
		easyTracker = EasyTracker.getInstance(activity);
	}

	@Override
	public void stopTracking(Activity activity) {
		EasyTracker.getInstance(activity).activityStop(activity);

	}

	@Override
	public void logEvent(String event) {
		if (event.contains(SCREEN_IDENTIFIER)) {
			event = event.replace(SCREEN_IDENTIFIER, "");
			easyTracker.set(Fields.SCREEN_NAME, event);
			easyTracker.send(MapBuilder.createAppView().build());
		} else {
			logEvent("Android", event);
		}

	}

	@Override
	public void logEvent(String category, String event) {
		easyTracker.send(MapBuilder.createEvent(category, event, null, null).build());
	}

	@Override
	public void logEvent(String category, String event, String label) {
		easyTracker.send(MapBuilder.createEvent(category, event, label, null).build());
	}

	@Override
	public void logEvent(String category, String event, String label, long value) {
		easyTracker.send(MapBuilder.createEvent(category, event, label, value).build());

	}

}
