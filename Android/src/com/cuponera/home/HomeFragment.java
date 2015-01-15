package com.cuponera.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.analytics.AnalyticsHelpers;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnalyticsHelpers.getInstance().logScreen(AnalyticsHelpers.HOME);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		StateRequest stateRequest = new StateRequest(getActivity()) {

			@Override
			protected void serviceReady(StateResponse result) {
				if (result != null && result.getState().size() > 0) {
					if ((!Settings.getInstance(getActivity()).isLocationEnable() || !LocationServices.getInstance(getActivity()).isLocationEnabled())
							&& Settings.getInstance(getBaseActivity()).getLatitude() == 0) {
						getBaseActivity().dynamicPopup(result.getState());
					}
				}
			}
		};
		if (!stateRequest.isResultCached()) {
			stateRequest.execute();
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
				logEvents(getResources().getString(R.string.menu_hotel));
				getBaseActivity().openStore(Const.HOTEL, getResources().getString(R.string.menu_hotel));
				break;
			case R.id.gastronomic:
				logEvents(getResources().getString(R.string.menu_grastronomic));
				getBaseActivity().openStore(Const.GASTRONOMIC, getResources().getString(R.string.menu_grastronomic));
				break;
			case R.id.store:
				logEvents(getResources().getString(R.string.menu_store));
				getBaseActivity().openStore(Const.STORE, getResources().getString(R.string.menu_store));
				break;
			case R.id.beach:
				logEvents(getResources().getString(R.string.menu_beach));
				getBaseActivity().openStore(Const.BEACH, getResources().getString(R.string.menu_beach));
				break;
			case R.id.highlighted:
				logEvents(getResources().getString(R.string.menu_highlighted));
				getBaseActivity().startFragment(new OfferFragment());
				break;
			case R.id.cinema:
				logEvents(getResources().getString(R.string.menu_cinema));
				getBaseActivity().openStore(Const.CINEMA, getResources().getString(R.string.menu_cinema));
				break;
			case R.id.munucipio:
				logEvents("http://www.lacosta.gov.ar/");
				getBaseActivity().openURL("http://www.lacosta.gov.ar/");
				break;
			}

		}
	};

	private void logEvents(String event) {
		AnalyticsHelpers.getInstance().logScreenWithEvent(AnalyticsHelpers.HOME, event);
	}

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

}
