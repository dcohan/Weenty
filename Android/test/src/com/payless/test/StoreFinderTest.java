package com.payless.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isRoot;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.payless.R;
import com.payless.home.HomeActivity;

public class StoreFinderTest extends BaseTest<HomeActivity> {

	public StoreFinderTest() {
		super(HomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@SuppressWarnings("unchecked")
	public void testStoreFinder() {
		mActivity = getActivity();

		onView(withId(R.id.menuButton)).perform(click());
		wait(1);
		onView(withId(R.id.menuButtonStores)).perform(click());

		// First case
		onView(withId(R.id.radio_zipcode)).perform(click());

		onView(withId(R.id.zipcode_text)).check(matches(withText("")));
		onView(withId(R.id.zipcode_text)).perform(ViewActions.typeText("11111"));

		onView(withId(R.id.clear_button)).perform(click());

		onView(withId(R.id.zipcode_text)).check(matches(withText("")));
		onView(withId(R.id.zipcode_text)).perform(ViewActions.typeText("66611"));

		onView(withId(R.id.search_button)).perform(click());

		wait(2);

		onView(isRoot()).perform(ViewActions.pressBack());

		// Second case
		onView(withId(R.id.radio_city)).perform(click());

		onView(withId(R.id.city_text)).check(matches(withText("")));
		onView(withId(R.id.city_text)).perform(ViewActions.typeText("New York"));

		onView(withId(R.id.stateSpinner)).perform(ViewActions.click());

		onData(allOf(is(instanceOf(String.class)), is("New York"))).perform(click());

		onView(withId(R.id.search_button)).perform(click());

		wait(2);

		onView(isRoot()).perform(ViewActions.pressBack());

//		 //Third case
//		 onView(withId(R.id.radio_location)).perform(click());
//		 onView(withId(R.id.search_button)).perform(click());
//		 wait(5);

	}

}
