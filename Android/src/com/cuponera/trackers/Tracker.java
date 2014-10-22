package com.cuponera.trackers;

import android.app.Activity;

public interface Tracker {

	public void startTracking(Activity activity);

	public void stopTracking(Activity activity);

	public void logEvent(String event);

	public void logEvent(String category, String event);

	public void logEvent(String category, String event, String label);

	public void logEvent(String category, String event, String label, long value);
}
