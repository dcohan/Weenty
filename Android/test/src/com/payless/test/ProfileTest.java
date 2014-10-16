package com.payless.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.payless.R;
import com.payless.prehome.PreHomeActivity;

public class ProfileTest extends BaseTest<PreHomeActivity> {
	
	public ProfileTest() {
		super(PreHomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testProfile() {
		mActivity = getActivity();

		wait(5);
		
		onView(withId(R.id.menuButton)).perform(click());
		onView(withId(R.id.menuButtonProfile)).perform(click());
		onView(withId(R.id.first_name)).perform(ViewActions.clearText());
		onView(withId(R.id.first_name)).perform(ViewActions.typeText("Joseph"));

		onView(withId(R.id.last_name)).perform(ViewActions.clearText());
		onView(withId(R.id.last_name)).perform(ViewActions.typeText("Smith"));

		onView(withId(R.id.birth_date)).perform(click());

//		onView(withText("13")).perform(ViewActions.typeText("22"));
		onView(withId(android.R.id.button1)).perform(click());
		onView(withId(R.id.radio_male)).perform(click());
		onView(withId(R.id.radio_female)).perform(click());

		onView(withId(R.id.cell_phone)).perform(ViewActions.clearText());
		onView(withId(R.id.cell_phone)).perform(ViewActions.typeText("12345678"));
		
		onView(withId(R.id.email)).perform(ViewActions.scrollTo());
		onView(withId(R.id.email)).perform(ViewActions.clearText());
		onView(withId(R.id.email)).perform(ViewActions.typeText("test@test.com"));

		onView(withId(R.id.address_line_1)).perform(ViewActions.scrollTo());
		onView(withId(R.id.address_line_1)).perform(ViewActions.clearText());
		onView(withId(R.id.address_line_1)).perform(ViewActions.typeText("1 Columbia, Suite 250"));

		onView(withId(R.id.address_line_2)).perform(ViewActions.scrollTo());
		onView(withId(R.id.address_line_2)).perform(ViewActions.clearText());
		onView(withId(R.id.address_line_2)).perform(ViewActions.typeText("1 Columbia, Suite 250"));

		onView(withId(R.id.city)).perform(ViewActions.scrollTo());
		onView(withId(R.id.city)).perform(ViewActions.clearText());
		onView(withId(R.id.city)).perform(ViewActions.typeText("New York"));
		

		onView(withId(R.id.state)).perform(ViewActions.scrollTo());
		onView(withId(R.id.state)).perform(click());
		String[] strings = mActivity.getResources().getStringArray(R.array.state_names);
		onView(withText(strings[4])).perform(click());

		onView(withId(R.id.zip_code)).perform(ViewActions.scrollTo());
		onView(withId(R.id.zip_code)).perform(ViewActions.clearText());
		onView(withId(R.id.zip_code)).perform(ViewActions.typeText("66611"));
		
		onView(withId(R.id.terms_and_conditions)).perform(ViewActions.scrollTo());
		
		onView(withId(R.id.checkbox_push_notifications)).perform(click());
		onView(withId(R.id.checkbox_text_message)).perform(click());
		onView(withId(R.id.checkbox_email)).perform(click());
		onView(withId(R.id.checkbox_direct_email)).perform(click());
		
		onView(withId(R.id.submitButton)).perform(click());
		
		
	}

}
