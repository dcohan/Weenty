package com.cuponera;

import android.app.Application;

import com.cuponera.service.ServiceConfig;
import com.cuponera.setup.Installation;

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
