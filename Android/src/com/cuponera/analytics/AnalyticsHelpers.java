package com.cuponera.analytics;

import android.app.Application;

import com.cuponera.utils.Const;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsHelpers extends Application {

	public static final String HOME = "Inicio";
	public static final String MORE = "Mas";
	public static final String PREFERENCES = "Preferencias";
	public static final String SEARCH = "Search";
	public static final String HIGHLIGHTED = "Destacados";
	public static final String ADMINISTRATION = "Administracion";
	public static final String WEATHER = "Clima";
	public static final String MAP_ALL = "Mapa Todos";
	public static final String MAP = "Mapa";

	private static AnalyticsHelpers instance;
	private Tracker tracker;

	public AnalyticsHelpers() {
		super();
	}

	public static AnalyticsHelpers getInstance() {
		if (instance == null) {
			instance = new AnalyticsHelpers();
		}
		return instance;
	}

	synchronized Tracker getTracker() {
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		tracker = analytics.newTracker(Const.GAId);
		return tracker;
	}

	public void logScreen(String screen) {
		if (tracker == null) {
			tracker = getTracker();
		}

		tracker.setScreenName(screen);
		tracker.send(new HitBuilders.AppViewBuilder().build());

	}

	public void logScreenWithEvent(String screen, String event) {
		if (tracker == null) {
			tracker = getTracker();
		}
		tracker.send(new HitBuilders.EventBuilder().setCategory(screen).setAction(event).setLabel(null).build());
	}
}
