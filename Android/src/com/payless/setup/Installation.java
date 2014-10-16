package com.payless.setup;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.payless.settings.Settings;
import com.payless.utils.Utils;

public class Installation {
    private static final String INSTALLATION_DEVICE = "PAYLESS_INSTALLATION_DEVICE";
    private static final String INSTALLATION_IDENTITY = "PAYLESS_INSTALLATION_IDENTITY";

    private static String deviceId = null;

	public synchronized static String getDeviceId(Context context) {
        
    	if (deviceId == null) {
    		
			deviceId = readDeviceIdFile(context);
			if (Utils.isBlankOrZero(deviceId))
			{
				deviceId=getSettings(context).getDeviceId();
    		
				if (Utils.isBlankOrZero(deviceId))
	    		{
	            	deviceId = getIMEI(context);
	            	
	            	//If it doesn't have IMEI, use the randomUDID feature.
	            	if(Utils.isBlankOrZero(deviceId)) {
	            		deviceId = UUID.randomUUID().toString();
	            	}
	    		}
	    	}                
			getSettings(context).setDeviceId(deviceId);
        }
        return deviceId;
    }
    
	private static Settings getSettings(Context context) {
		return Settings.getInstance(context);
	}
	
	private static String readDeviceIdFile(Context context) {
		//Legacy :(
		File installation = new File(context.getFilesDir(), INSTALLATION_DEVICE);

		if (installation.exists()){
		
			try {
				RandomAccessFile f = new RandomAccessFile(installation, "r");
				byte[] bytes;
				bytes = new byte[(int) f.length()];
				f.readFully(bytes);
				f.close();
				removeLegacyInstalationFiles(context);
				return new String(bytes);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
    }
    
    private static void removeLegacyInstalationFiles(Context context) {
		File file = new File(context.getFilesDir(), INSTALLATION_DEVICE);
		if (file .exists())
			file .delete();
		
		file  = new File(context.getFilesDir(), INSTALLATION_IDENTITY);
		if (file .exists())
			file .delete();
	}

	private static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();

		return imei;
	}
}
