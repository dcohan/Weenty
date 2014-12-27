package com.cuponera.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.State;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.navigation.NavBarFragment;
import com.cuponera.product.OfferFragment;
import com.cuponera.service.state.StateRequest;
import com.cuponera.service.state.StateResponse;
import com.cuponera.settings.Settings;
import com.cuponera.utils.Const;
import com.cuponera.utils.LocationServices;
import com.cuponera.utils.Utils;

public class HomeFragment extends BaseFragment implements HeaderInterface {

	@Override
	protected int getLayout() {
		return R.layout.fragment_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (!LocationServices.getInstance(getActivity()).isLocationEnabled() && Settings.getInstance(getBaseActivity()).getLatitude() == 0) {

			StateRequest stateRequest = new StateRequest(getActivity()) {

				@Override
				public void onServiceReturned(StateResponse result) {
					if (result != null && result.getState().size() > 0)
						dynamicPopup(result.getState());
				}
			};
			stateRequest.execute();

		} else {
			getMuni();
		}
		mViewProxy.findTextView(R.id.hotel).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.gastronomic).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.store).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.beach).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.highlighted).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.cinema).setOnClickListener(dashboardListener);
		mViewProxy.findImageView(R.id.munucipio).setOnClickListener(dashboardListener);
		Utils.setCalibri(getActivity(), mViewProxy.findTextView(R.id.explore));

	}

	private OnClickListener dashboardListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.hotel:
				getBaseActivity().openStore(Const.HOTEL, getResources().getString(R.string.menu_hotel));
				break;
			case R.id.gastronomic:
				getBaseActivity().openStore(Const.GASTRONOMIC, getResources().getString(R.string.menu_grastronomic));
				break;
			case R.id.store:
				getBaseActivity().openStore(Const.STORE, getResources().getString(R.string.menu_store));
				break;
			case R.id.beach:
				getBaseActivity().openStore(Const.BEACH, getResources().getString(R.string.menu_beach));
				break;
			case R.id.highlighted:
				getBaseActivity().startFragment(new OfferFragment());
				break;
			case R.id.cinema:
				getBaseActivity().openStore(Const.CINEMA, getResources().getString(R.string.menu_cinema));
				break;
			case R.id.munucipio:
				getBaseActivity().openURL("http://www.lacosta.gov.ar/");
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
					mViewProxy.findImageView(R.id.munucipio).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							getBaseActivity().openURL("http://www.lacosta.gov.ar/");

						}
					});
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean showMuni(List<Address> addresses) {
		return addresses.size() > 0 && addresses.get(0).getLocality() != null;
	}

	private void dynamicPopup(final ArrayList<State> statesArray) {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
		builderSingle.setTitle("Elegir");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
		for (State state : statesArray) {
			arrayAdapter.add(state.getName());
		}
		builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (State state : statesArray) {
					if (state.getName().equalsIgnoreCase(arrayAdapter.getItem(which))) {
						Settings.getInstance(getActivity()).setLatitude(state.getLatitude());
						Settings.getInstance(getActivity()).setLongitude(state.getLongitude());
						Settings.getInstance(getActivity()).setCity(state.getName());
						NavBarFragment navBarFragment = (NavBarFragment) getBaseActivity().getSupportFragmentManager().findFragmentById(R.id.navBar);
						if (navBarFragment != null) {
							navBarFragment.setTitle(state.getName());
						}
						getMuni();
					}
				}
			}
		});
		builderSingle.show();
	}
}
