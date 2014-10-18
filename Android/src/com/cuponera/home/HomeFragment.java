package com.cuponera.home;

import android.os.Bundle;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWeather();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

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
	
	private void getWeather(){
		mYahooWeather.setExceptionListener(this);
		getBaseActivity().showLoading();
		mYahooWeather.setNeedDownloadIcons(true);
		mYahooWeather.setSearchMode(SEARCH_MODE.GPS);
		mYahooWeather.queryYahooWeatherByGPS(getBaseActivity().getApplicationContext(), this);
	}

	@Override
	public void onFailConnection(Exception e) {
		getBaseActivity().hideLoading();

	}

	@Override
	public void onFailParsing(Exception e) {
		getBaseActivity().hideLoading();

	}

	@Override
	public void onFailFindLocation(Exception e) {
		getBaseActivity().hideLoading();

	}

	@Override
	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		getBaseActivity().hideLoading();
		if (weatherInfo != null) {
			mViewProxy.findTextView(R.id.textview_forecast_info).setText(
					weatherInfo.getTitle() + "\n" + weatherInfo.getWOEIDneighborhood() + ", " + weatherInfo.getWOEIDCounty() + ", "
							+ weatherInfo.getWOEIDState() + ", " + weatherInfo.getWOEIDCountry());
			if (weatherInfo.getCurrentConditionIcon() != null) {
				mViewProxy.findImageView(R.id.imageview_forecast_info).setImageBitmap(weatherInfo.getCurrentConditionIcon());
			}

		}
	}

}
