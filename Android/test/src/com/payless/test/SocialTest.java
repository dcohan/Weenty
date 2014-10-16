package com.payless.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isRoot;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.payless.R;
import com.payless.home.HomeActivity;

public class SocialTest extends BaseTest<HomeActivity> {

	public SocialTest() {
		super(HomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testSocial() throws Throwable {
		mActivity = getActivity();

		onView(withId(R.id.menuButton)).perform(click());
		wait(1);
		onView(withId(R.id.menuButtonSocial)).perform(click());
		
		for(int i = 0; i < 5; i++) {
			onView(withId(R.id.list)).perform(swipeDown());
		}

		onView(withId(R.id.list)).perform(click());

		wait(5);
		
		onView(isRoot()).perform(ViewActions.pressBack());
	}

}
