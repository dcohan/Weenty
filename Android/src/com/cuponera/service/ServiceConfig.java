package com.cuponera.service;

import android.content.Context;

import com.cuponera.service.config.BuildConfig;

public class ServiceConfig {
	public final static String LOG_TAG = "com.cuponera.service";

	public final static String API_PROTOCOL = BuildConfig.endpoint.getApiProtocol();
	public final static String API_HOST = BuildConfig.endpoint.getApiHost();
	public static final String API_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SERVICE_VERSION = "2";
	public static final String SOURCE_PARAM = "MOBILE APP - ANDROID";
	public static final int APP_TYPE_TABLET = 2;
	public static final int APP_TYPE_PHONE = 1;
	public static final float LOCATION_UPDATES_MIN_DISTANCE = 200.0f; // 200
	public static final long LOCATION_UPDATES_MIN_TIME = 60000; // 60 seconds

	private static String deviceId = null;

	public static String getDeviceId() {
		return deviceId;
	}

	public static void setDeviceId(String deviceId) {
		ServiceConfig.deviceId = deviceId;
	}

}
