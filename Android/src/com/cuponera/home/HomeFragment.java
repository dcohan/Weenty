package com.cuponera.home;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.settings.Settings;
import com.cuponera.utils.Const;

public class HomeFragment extends BaseFragment implements HeaderInterface {

	@Override
	protected int getLayout() {
		return R.layout.fragment_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewProxy.findTextView(R.id.hotel).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.gastronomic).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.coffee).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.beach).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.shopping).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.cinema).setOnClickListener(dashboardListener);
		getMuni();
	}

	private OnClickListener dashboardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.hotel:
				getBaseActivity().openProduct(Const.HOTEL);
				break;
			case R.id.gastronomic:
				getBaseActivity().openProduct(Const.GASTRONOMIC);
				break;
			case R.id.coffee:
				getBaseActivity().openProduct(Const.COFEE);
				break;
			case R.id.beach:
				getBaseActivity().openProduct(Const.BEACH);
				break;
			case R.id.shopping:
				getBaseActivity().openProduct(Const.SHOPPING);
				break;
			case R.id.cinema:
				getBaseActivity().openProduct(Const.CINEMA);
				break;
			}

		}
	};

	@Override
	public String getTitle() {
		return getString(R.string.home_title);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	@Override
	protected boolean showHomeButton() {
		return false;
	}

	private void getMuni() {
		double latitude = Settings.getInstance(getActivity()).getLatitude();
		double longitude = Settings.getInstance(getActivity()).getLongitude();

		if (latitude != 0 && longitude != 0) {
			Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
			try {
				List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
				if (showMuni(addresses)) {
					mViewProxy.findImageView(R.id.munucipio).setVisibility(View.VISIBLE);
					mViewProxy.findImageView(R.id.munucipio).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							getBaseActivity().openURL("http://google.com");

						}
					});
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean showMuni(List<Address> addresses) {
		if (addresses.size() > 0) {
			if (addresses.get(0).getLocality() != null) {
				return true;
			}
		}
		return false;
	}
}
