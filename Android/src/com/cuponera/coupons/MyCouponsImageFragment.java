package com.cuponera.coupons;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.helpers.AnalyticsHelper;
import com.cuponera.model.Coupon;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.utils.Utils;

public class MyCouponsImageFragment extends BaseFragment implements HeaderInterface {

	public final static String COUPONS = "coupon";

	@Override
	public String getTitle() {
		return getString(R.string.special_deal);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_coupons_image;
	}

	public static MyCouponsImageFragment newInstance(Coupon coupon) {
		MyCouponsImageFragment myCouponsImageFragment = new MyCouponsImageFragment();
		Bundle b = new Bundle();
		b.putParcelable(COUPONS, coupon);
		myCouponsImageFragment.setArguments(b);
		return myCouponsImageFragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final Coupon coupon = getArguments().getParcelable(COUPONS);

		Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.image), coupon.getImageURL());

		mViewProxy.findImageView(R.id.image).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_COUPONS, AnalyticsHelper.DETAILS, coupon.getPromoCode());
				getBaseActivity().openURL(coupon.getTargetURL(), coupon.getTitle());
			}
		});

	}

	@Override
	public HeaderImageInterface getRightImage() {
		// TODO Auto-generated method stub
		return null;
	}

}