package com.cuponera.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		WeatherRequest request = new WeatherRequest(getActivity()) {

			@SuppressLint("DefaultLocale")
			@Override
			protected void serviceReady(WeatherResponse result) {
				if (result != null && result.getMainWeather() != null && result.getMainWeather().size() > 0 && result.getMainWeather() != null) {
					Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image), "http://openweathermap.org/img/w/"
							+ result.getMainWeather().get(0).getWeather().get(0).getIcon() + ".png");
					mViewProxy.findTextView(R.id.min_temp).setText((int) result.getMainWeather().get(0).getTemp().getMin() + "¡");
					mViewProxy.findTextView(R.id.max_temp).setText((int) result.getMainWeather().get(0).getTemp().getMax() + "¡");
					mViewProxy.findTextView(R.id.humidity).setText((int) result.getMainWeather().get(0).getHumidity() + "%");

					String weekDay;
					SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					weekDay = dayFormat.format(calendar.getTime());
					mViewProxy.findTextView(R.id.day_1).setText(weekDay);

					calendar.add(Calendar.DAY_OF_MONTH, 1);
					weekDay = dayFormat.format(calendar.getTime());
					mViewProxy.findTextView(R.id.day_2).setText(weekDay);

					calendar.add(Calendar.DAY_OF_MONTH, 1);
					weekDay = dayFormat.format(calendar.getTime());
					mViewProxy.findTextView(R.id.day_3).setText(weekDay);

					Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image_tomorrow), "http://openweathermap.org/img/w/"
							+ result.getMainWeather().get(1).getWeather().get(0).getIcon() + ".png");
					mViewProxy.findTextView(R.id.min_temp_tomorrow).setText((int) result.getMainWeather().get(1).getTemp().getMin() + "¡");
					mViewProxy.findTextView(R.id.max_temp_tomorrow).setText((int) result.getMainWeather().get(1).getTemp().getMax() + "¡");

					Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image_tomorrow2), "http://openweathermap.org/img/w/"
							+ result.getMainWeather().get(2).getWeather().get(0).getIcon() + ".png");
					mViewProxy.findTextView(R.id.min_temp_tomorrow_2).setText((int) result.getMainWeather().get(2).getTemp().getMin() + "¡");
					mViewProxy.findTextView(R.id.max_temp_tomorrow_2).setText((int) result.getMainWeather().get(2).getTemp().getMax() + "¡");

					Utils.loadImageFromUrl(getActivity(), mViewProxy.findImageView(R.id.weather_image_tomorrow3), "http://openweathermap.org/img/w/"
							+ result.getMainWeather().get(3).getWeather().get(0).getIcon() + ".png");
					mViewProxy.findTextView(R.id.min_temp_tomorrow_3).setText((int) result.getMainWeather().get(3).getTemp().getMin() + "¡");
					mViewProxy.findTextView(R.id.max_temp_tomorrow_3).setText((int) result.getMainWeather().get(3).getTemp().getMax() + "¡");

				} else {
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_RESULTS_FOUND));
					getBaseActivity().onHomeButton();
				}

			}
		};
		if (!request.isResultCached()) {
			request.execute(false);
		}
	}
}
