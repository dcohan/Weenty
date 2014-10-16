package com.payless.coupons;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.payless.BaseFragment;
import com.payless.R;
import com.payless.model.Coupon;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.utils.WebViewFragment;

public class MyCouponsFragment extends BaseFragment implements HeaderInterface {
	public final static String COUPONS_ID = "couponId";

	public static MyCouponsFragment newInstance(String couponId) {
		MyCouponsFragment myCouponsFragment = new MyCouponsFragment();
		Bundle b = new Bundle();
		b.putString(COUPONS_ID, couponId);
		myCouponsFragment.setArguments(b);
		return myCouponsFragment;
	}

	@Override
	public String getTitle() {
		return getString(R.string.menu_coupons);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_coupons;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		MyCouponsAdapter adapter;
		ListView couponsList = mViewProxy.findListView(R.id.coupons_listview);
		
		List<Coupon> coupons = getBaseActivity().getSettings().getCoupons().getCoupons();
		adapter = new MyCouponsAdapter(getActivity(), coupons);
		
		if (getArguments() != null && getArguments().getString(COUPONS_ID) != null) {

			int id = Integer.valueOf(getArguments().getString(COUPONS_ID));
			if(id != 0) {
				for(int i = 0; i < coupons.size(); i++) {
					if (coupons.get(i).getIdCoupon() == id) {
						adapter.getView(i, null, null).performClick();
						break;
					}
				}
			}
			getArguments().remove(COUPONS_ID);
		}
		couponsList.setAdapter(adapter);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return new HeaderImageInterface() {

			@Override
			public void onClickListener() {
				getBaseActivity().openURL(WebViewFragment.TERMS_AND_CONDITIONS, getString(R.string.terms_and_conditions));
			}

			@Override
			public Drawable getDrawable() {
				return getBaseActivity().getResources().getDrawable(R.drawable.helpicon);
			}
		};
	}
}
