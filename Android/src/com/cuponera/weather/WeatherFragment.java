package com.cuponera.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.service.weather.WeatherRequest;
import com.cuponera.service.weather.WeatherResponse;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;

public class WeatherFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_weather;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WeatherRequest request = new WeatherRequest(getActivity()) {

			@SuppressLint("DefaultLocale")
			@Override
			public void onServiceReturned(WeatherResponse result) {
				if (result != null && result.getWeather() != null && result.getWeather().size() > 0 && result.getMainWeather() != null) {
					Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image), "http://openweathermap.org/img/w/"
							+ result.getWeather().get(0).getIcon() + ".png");
					//mViewProxy.findTextView(R.id.min_temp).setText(text);
				} else {
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_RESULTS_FOUND));
					getBaseActivity().onHomeButton();
				}
			}
		};

		request.execute(false);
	}
}
