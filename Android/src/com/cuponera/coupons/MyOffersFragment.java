package com.cuponera.coupons;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Coupon;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;

public class MyOffersFragment extends BaseFragment implements HeaderInterface {
	public final static String COUPONS_ID = "couponId";

	public static MyOffersFragment newInstance(String couponId) {
		MyOffersFragment myCouponsFragment = new MyOffersFragment();
		Bundle b = new Bundle();
		b.putString(COUPONS_ID, couponId);
		myCouponsFragment.setArguments(b);
		return myCouponsFragment;
	}

	@Override
	public String getTitle() {
		return getString(R.string.menu_ofertas);
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
				getBaseActivity().openURL(null, getString(R.string.terms_and_conditions));
			}

			@Override
			public Drawable getDrawable() {
				return getBaseActivity().getResources().getDrawable(R.drawable.helpicon);
			}
		};
	}
}
