package com.payless;

import android.app.Application;

import com.payless.service.ServiceConfig;
import com.payless.setup.Installation;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		//Configure service
		ServiceConfig.setDeviceId(Installation.getDeviceId(getApplicationContext()));
	}

    @Override
    public void onLowMemory(){
    	
    }
}
