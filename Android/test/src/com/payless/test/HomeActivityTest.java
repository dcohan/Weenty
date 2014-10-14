package com.payless.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isRoot;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.payless.R;
import com.payless.home.HomeActivity;

public class HomeActivityTest extends BaseTest<HomeActivity> {

	public HomeActivityTest() {
		super(HomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
	}

	public void testHomeActivity() {
		assertNotNull(mActivity);
		onView(withId(R.id.list)).perform(ViewActions.swipeLeft());
		onView(withId(R.id.list)).perform(ViewActions.swipeLeft());
		onView(withId(R.id.list)).perform(ViewActions.swipeRight());

		onView(withId(R.id.list)).perform(ViewActions.click());
		wait(5);
//		new GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER);
//		onView(isRoot()).perform(ViewActions.pressBack());
	}

}
