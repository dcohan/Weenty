package com.cuponera.utils;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.navigation.NavBarFragment;
import com.cuponera.settings.Settings;

public class LocationServices implements LocationListener {

	private static int TimeInterval = 900000; // 15 minutes
	private static int DistanceInterval = 500; // 500 meters
	private static int MaxTimeWaitingGPSLocation = 10000; // 10 seconds
	private static int FAKE_ACCURACY = 123123123;

	public static LocationServices instance;

	private Context context;
	private LocationManager locationManager;
	private Location location;
	private Handler locationRequestTimeOutHandler;

	private LocationServices() {
		// Singleton
	}

	private LocationServices(Context context) {
		this.context = context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	public static LocationServices getInstance(Context context) {
		if (instance == null || instance.context != context) {
			instance = new LocationServices(context);
		}

		return instance;
	}

	public void startTracking() {

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TimeInterval, DistanceInterval, this);
	}

	public void stopTracking() {
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(final Location location) {
		this.setLocation(location);
		Log.d("location", location.toString());
		Settings.getInstance(context).setLongitude(location.getLongitude());
		Settings.getInstance(context).setLatitude(location.getLatitude());

		Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
		try {
			List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			String finalAddress = address.get(0).getLocality();
			Settings.getInstance(context).setCity(finalAddress);
			NavBarFragment navBarFragment = (NavBarFragment) ((BaseActivity) context).getSupportFragmentManager().findFragmentById(R.id.navBar);
			if (navBarFragment != null) {
				navBarFragment.setTitle(finalAddress);
			}

		} catch (Exception e) {
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
		setLocation(null);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isLocationEnabled() {
		return Settings.getInstance(context).isLocationEnable() && (isAccurateLocationEnabled() || isNetworkLocationEnabled());
	}

	public boolean isAccurateLocationEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public boolean isNetworkLocationEnabled() {
		return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	public void requestAccurateLocation(final RequestLocationListener listener) {
		final Date currentDate = new Date(System.currentTimeMillis());
		// Back door for QA to be able to test the location faking it.
		if (location != null && location.hasAccuracy() && location.getAccuracy() == FAKE_ACCURACY) {
			listener.onLocationReceived(location);
		} else {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			String bestProvider = locationManager.getBestProvider(criteria, true);

			if (location != null && currentDate.getTime() - location.getTime() <= TimeInterval && location.getProvider().equals(bestProvider)) {
				listener.onLocationReceived(location);
			} else {

				final LocationListener singleLocationListener = new LocationListener() {
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						listener.onLocationReceived(location);
					}

					@Override
					public void onProviderEnabled(String provider) {
						Log.d("location", "enabled");
					}

					@Override
					public void onProviderDisabled(String provider) {
						Log.d("location", "disabled");
					}

					@Override
					public void onLocationChanged(Location location) {
						location.setTime(currentDate.getTime());
						LocationServices.this.location = location;
					}
				};

				locationRequestTimeOutHandler = new Handler();
				locationRequestTimeOutHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (location != null) {
							listener.onLocationReceived(location);
							locationManager.removeUpdates(singleLocationListener);
						}
					}
				}, MaxTimeWaitingGPSLocation);

				locationManager.requestSingleUpdate(bestProvider, singleLocationListener, null);
			}
		}
	}

	public interface RequestLocationListener {
		public void onLocationReceived(Location location);
	}

}
