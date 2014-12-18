package com.cuponera.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.weather.WeatherRequest;
import com.cuponera.service.weather.WeatherResponse;
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
				Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image), "http://openweathermap.org/img/w/"
						+ result.getWeather().get(0).getIcon() + ".png");
				mViewProxy.findTextView(R.id.weather_temp).setText(
						"En estos momentos hay " + (int) result.getMainWeather().getTemp() + "° y una humedad del "
								+ (int) result.getMainWeather().getHumidity() + "%. La presion es de " + result.getMainWeather().getPressure() + " milibares.");

			}
		};

		request.execute(false);
	}
}
