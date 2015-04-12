package com.cuponera;

import android.app.Application;
import android.content.pm.ActivityInfo;

import com.cuponera.service.ServiceConfig;
import com.cuponera.setup.Installation;
import com.cuponera.utils.Utils;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Configure service
		ServiceConfig.setDeviceId(Installation.getDeviceId(getApplicationContext()));
	}

	@Override
	public void onLowMemory() {

	}
}
