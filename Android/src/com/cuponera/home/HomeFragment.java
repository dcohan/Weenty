package com.cuponera.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.HomeOffer;
import com.cuponera.model.HomeOffers;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.utils.ViewProxy;

public class HomeFragment extends BaseFragment implements HeaderInterface {

	private ViewPager mViewPager;
	private HomeOffers homeOffers;
	private LinearLayout navigationDots;

	@Override
	protected int getLayout() {
		return R.layout.fragment_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		homeOffers = getBaseActivity().getSettings().getHomeOffers();
		if (homeOffers != null && homeOffers.getOffers() != null) {
			mViewPager = (ViewPager) mViewProxy.findView(R.id.list);

			final HomeGalleryAdapter adapter = new HomeGalleryAdapter(getChildFragmentManager());
			adapter.setData(homeOffers);

			mViewPager.setAdapter(adapter);
			mViewPager.setCurrentItem(1);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					if (adapter.hasInfiniteScroll() && arg0 == adapter.getCount() - 1) {
						arg0 = 1;
					} else if (adapter.hasInfiniteScroll() && arg0 == 0) {
						arg0 = adapter.getCount() - 2;
					}
					enableNavigationDotAtIndex(arg0 - 1);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					if (arg1 == 0) {
						if (adapter.hasInfiniteScroll() && arg0 == adapter.getCount() - 1) {
							mViewPager.setCurrentItem(1, false);
						} else if (adapter.hasInfiniteScroll() && arg0 == 0) {
							mViewPager.setCurrentItem(adapter.getCount() - 2, false);
						}
					}

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});

			setNavigationDots();

		}

	}

	public void setNavigationDots() {
		navigationDots = mViewProxy.findLinearLayout(R.id.navigationDots);

		for (HomeOffer offer : homeOffers.getOffers()) {
			ViewProxy dotProxy = new ViewProxy(getBaseActivity(), R.layout.fragment_navigation_dot);
			navigationDots.addView(dotProxy.getView(), homeOffers.getOffers().indexOf(offer));
		}
		enableNavigationDotAtIndex(0);
	}

	public void enableNavigationDotAtIndex(int index) {

		for (int i = 0; i < navigationDots.getChildCount(); i++) {
			View v = navigationDots.getChildAt(i);
			if (v != null) {
				if (v.findViewById(R.id.dot) != null) {
					boolean enabled = (index == i) ? false : true;
					((ImageView) v.findViewById(R.id.dot)).setEnabled(enabled);
				}
			}
		}

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
