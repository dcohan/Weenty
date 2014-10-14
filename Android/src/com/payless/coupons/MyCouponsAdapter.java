package com.payless.coupons;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cuponera.R;
import com.payless.BaseActivity;
import com.payless.helpers.AnalyticsHelper;
import com.payless.model.Coupon;
import com.payless.utils.BaseListAdapter;
import com.payless.utils.ValidationUtils;
import com.payless.utils.ViewProxy;

public class MyCouponsAdapter extends BaseListAdapter {
	private Activity activity;
	private List<Coupon> coupon;

	public MyCouponsAdapter(Activity activity, List<Coupon> coupon) {
		this.activity = activity;
		this.coupon = coupon;
	}

	@Override
	public int getCount() {
		return coupon != null ? coupon.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_my_coupons);
		final Coupon c = coupon.get(position);
		mViewProxy.findTextView(R.id.coupon_title).setText(c.getTitle());
		mViewProxy.findTextView(R.id.expires_coupon).setText(
				String.format(activity.getResources().getString(R.string.my_coupons_expires),
						ValidationUtils.formattedDateFromString(c.getExpirationDatetime())));
		mViewProxy.findTextView(R.id.promo_code_coupon).setText(
				String.format(activity.getResources().getString(R.string.my_coupons_promo_code), c.getPromoCode()));

		mViewProxy.findLinearLayout(R.id.adapter_coupon).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_COUPONS, AnalyticsHelper.LIST, c.getPromoCode());
				((BaseActivity) activity).pushFragment(MyCouponsImageFragment.newInstance(c), true);
			}
		});
		return mViewProxy.getView();
	}

}
