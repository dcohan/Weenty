package com.payless.test;


import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import android.content.Intent;
import android.net.Uri;

import com.payless.R;
import com.payless.home.HomeActivity;
import com.payless.model.Coupon;
import com.payless.model.Coupons;
import com.payless.settings.Settings;
import com.payless.utils.PushHandler;

public class DeepLinkingCasesTest extends BaseTest<HomeActivity> {

	public DeepLinkingCasesTest() {
		super(HomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHome() {
		setUpWithUrl(PushHandler.HOME);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.home_title)));
	}

	public void testShop() {
		setUpWithUrl(PushHandler.SHOP);
		wait(4);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.shop_title)));
	}

	public void testCoupons() {
		setUpWithUrl(PushHandler.COUPONS);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.menu_coupons)));
	}

	public void testCouponsWithID() {
		Coupons coupons = Settings.getInstance(mActivity).getCoupons();
		if(coupons != null
				&& coupons.getCoupons() != null
				&& coupons.getCoupons().size() > 0) {
			Coupon coupon = coupons.getCoupons().get(0);
			setUpWithUrl(PushHandler.COUPONS + "/" + coupon.getIdCoupon());
			onView(withId(R.id.headerTitle)).check(matches(withText(R.string.special_deal)));
		}
	}

	public void testLookBook() {
		setUpWithUrl(PushHandler.LOOKBOOK);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.look_book_title)));
	}

	public void testSocial() {
		setUpWithUrl(PushHandler.SOCIAL);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.social)));
	}

	public void testStores() {
		setUpWithUrl(PushHandler.STORES);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.stores_title)));
	}

	public void testProfile() {
		setUpWithUrl(PushHandler.PROFILE);
		onView(withId(R.id.headerTitle)).check(matches(withText(R.string.my_profile)));
	}

	public void testWebView() {
		setUpWithUrl(PushHandler.WEB_VIEW + "/http://google.com");
		wait(3);
	}

	public void testDeviceInfo() {
		setUpWithUrl(PushHandler.DEVICE_INFO);
	}
	
	private void setUpWithUrl(String url) {
		Intent intent = new Intent(getInstrumentation().getTargetContext(), HomeActivity.class);
		Uri uri = Uri.parse("payless://" + url);
		intent.setData(uri);
		intent.setAction(Intent.ACTION_VIEW);
		setActivityIntent(intent);
		mActivity = getActivity();
		assertNotNull(mActivity);
	}

}
