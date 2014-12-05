package com.cuponera.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.utils.Const;
import com.cuponera.utils.LocationServices;
import com.cuponera.yahoo.WeatherInfo;
import com.cuponera.yahoo.YahooWeather;
import com.cuponera.yahoo.YahooWeather.SEARCH_MODE;
import com.cuponera.yahoo.YahooWeatherExceptionListener;
import com.cuponera.yahoo.YahooWeatherInfoListener;

public class HomeFragment extends BaseFragment implements HeaderInterface, YahooWeatherInfoListener, YahooWeatherExceptionListener {

	private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, 5000, true);

	@Override
	protected int getLayout() {
		return R.layout.fragment_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getWeather();
		mViewProxy.findTextView(R.id.hotel).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.gastronomic).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.coffee).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.beach).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.shopping).setOnClickListener(dashboardListener);
		mViewProxy.findTextView(R.id.cinema).setOnClickListener(dashboardListener);
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

	private void getWeather() {
		if (LocationServices.getInstance(getActivity()).isLocationEnabled()) {
			mYahooWeather.setExceptionListener(this);
			mYahooWeather.setNeedDownloadIcons(true);
			mYahooWeather.setSearchMode(SEARCH_MODE.GPS);
			mYahooWeather.queryYahooWeatherByGPS(getBaseActivity().getApplicationContext(), this);
		} else {
			mViewProxy.findLinearLayout(R.id.weather_layout).setVisibility(View.GONE);
		}
	}

	@Override
	public void onFailConnection(Exception e) {
		getBaseActivity().hideLoading();
		mViewProxy.findLinearLayout(R.id.weather_layout).setVisibility(View.GONE);

	}

	@Override
	public void onFailParsing(Exception e) {
		getBaseActivity().hideLoading();
		mViewProxy.findLinearLayout(R.id.weather_layout).setVisibility(View.GONE);
	}

	@Override
	public void onFailFindLocation(Exception e) {
		mViewProxy.findLinearLayout(R.id.weather_layout).setVisibility(View.GONE);
	}

	@Override
	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		if (weatherInfo != null) {
			mViewProxy.findTextView(R.id.textview_forecast_info).setText(weatherInfo.getWOEIDCounty() + ", " + weatherInfo.getCurrentTempC() + " grados");
			if (weatherInfo.getCurrentConditionIcon() != null) {
				mViewProxy.findImageView(R.id.imageview_forecast_info).setImageBitmap(weatherInfo.getCurrentConditionIcon());
			}

		}
	}

}
