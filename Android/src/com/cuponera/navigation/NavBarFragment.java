package com.cuponera.navigation;

import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.analytics.AnalyticsHelpers;
import com.cuponera.navigation.MenuFragment.MenuInterface;
import com.cuponera.search.SearchFragment;
import com.cuponera.settings.Settings;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ValidationUtils;
import com.cuponera.weather.WeatherFragment;

public class NavBarFragment extends BaseFragment implements MenuInterface {

	private MenuFragment menu;
	private LinearLayout menuButton;
	private FrameLayout menuOutside;

	@Override
	protected int getLayout() {
		return R.layout.fragment_header;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		menu = (MenuFragment) getBaseActivity().getSupportFragmentManager().findFragmentById(R.id.menuFragment);

		menu.setDelegate(this);

		mViewProxy.findImageView(R.id.auxiliarImage).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.hideKeyboard(getActivity(), getView());
				AnalyticsHelpers.getInstance().logScreen(AnalyticsHelpers.SEARCH);
				getBaseActivity().startFragment(new SearchFragment());
			}
		});

		mViewProxy.findImageView(R.id.homeButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.hideKeyboard(getActivity(), getView());
				AnalyticsHelpers.getInstance().logScreen(AnalyticsHelpers.WEATHER);
				getBaseActivity().startFragment(new WeatherFragment());
			}
		});

		menuButton = mViewProxy.findLinearLayout(R.id.menuButton);

		Utils.setCalibri(getActivity(), (TextView) mViewProxy.findView(R.id.headerTitle));
		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getMenu().toggle();
			}
		});

		menuOutside = mViewProxy.findFrameLayout(R.id.menuOutside);
		menuOutside.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getMenu().close();
			}
		});

	}

	public void setTitle(String title) {
		if (ValidationUtils.isNullOrEmpty(Settings.getInstance(getActivity()).getCity())) {
			Settings.getInstance(getBaseActivity()).setLatitude(-36.68815440493361);
			Settings.getInstance(getBaseActivity()).setLongitude(-56.68224334716797);
			mViewProxy.findTextView(R.id.headerTitle).setText("SAN BERNARDO");
		} else {
			mViewProxy.findTextView(R.id.headerTitle).setText(Settings.getInstance(getActivity()).getCity().toUpperCase(Locale.ROOT));
		}
	}

	public MenuFragment getMenu() {
		return menu;
	}

	public void setMenu(MenuFragment menu) {
		this.menu = menu;
	}

	public void show() {
		mViewProxy.getView().setVisibility(View.VISIBLE);
	}

	public void hide() {
		mViewProxy.getView().setVisibility(View.GONE);
	}

	@Override
	public void onChangeState(boolean open) {
		menuButton.setActivated(open);
		if (open) {
			menuOutside.setVisibility(View.VISIBLE);
		} else {
			menuOutside.setVisibility(View.GONE);
		}
	}

	@Override
	public void onHomeButton() {
		getBaseActivity().onHomeButton();
	}

	@Override
	public void onAdminButton() {
		getBaseActivity().onAdminButton();
	}

	@Override
	public void onSpecialOffers() {
		getBaseActivity().onSpecialOffers();

	}

	@Override
	public void onMoreButton() {
		getBaseActivity().onMoreButton();
	}

	@Override
	public void onPreferenceButton() {
		getBaseActivity().onPreferenceButton();

	}

	@Override
	public void onSearchButton() {
		getBaseActivity().onSearchButton();

	}

}
