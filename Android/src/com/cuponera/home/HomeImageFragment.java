package com.cuponera.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.helpers.AnalyticsHelper;
import com.cuponera.model.HomeOffer;
import com.cuponera.utils.Utils;

public class HomeImageFragment extends BaseFragment {

	public final static String OFFER = "offer";

	@Override
	protected int getLayout() {
		return R.layout.fragment_home_image;
	}

	public static HomeImageFragment newInstance(HomeOffer offer) {
		HomeImageFragment homeImageFragment = new HomeImageFragment();
		Bundle b = new Bundle();
		b.putParcelable(OFFER, offer);
		homeImageFragment.setArguments(b);
		return homeImageFragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final HomeOffer offer = getArguments().getParcelable(OFFER);

		Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.image), offer.getImageURL());

		mViewProxy.findImageView(R.id.image).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategory(AnalyticsHelper.CARROUSEL, offer.getImageURL());
				getBaseActivity().openURL(offer.getTargetURL(), getString(R.string.home_title));
			}
		});

	}
}
