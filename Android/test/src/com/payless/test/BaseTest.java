package com.payless.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import com.google.android.apps.common.testing.ui.espresso.action.GeneralClickAction;
import com.google.android.apps.common.testing.ui.espresso.action.GeneralLocation;
import com.google.android.apps.common.testing.ui.espresso.action.GeneralSwipeAction;
import com.google.android.apps.common.testing.ui.espresso.action.Press;
import com.google.android.apps.common.testing.ui.espresso.action.Swipe;
import com.google.android.apps.common.testing.ui.espresso.action.Tap;
import com.payless.BaseActivity;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.event.EventListener;

public abstract class BaseTest<T extends BaseActivity> extends ActivityInstrumentationTestCase2<T> {

	T mActivity;
	protected String serverError;
	
	public BaseTest(Class<T> activityClass) {
		super(activityClass);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		EventBus.getInstance().addListener(mEventListener, ErrorEvent.class);
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if(serverError != null) {
			throw new Exception(serverError);
		}
	}

	protected final EventListener<ErrorEvent> mEventListener = new EventListener<ErrorEvent>() {

		@Override
		public void onEvent(final ErrorEvent event) {
			serverError = event.getExceptionMessage();
		}

	};
	
	public ViewAction swipeDown(){
		return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER);
	}
	
	public ViewAction tapCenter(){
		return new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER);
	}
	
	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void setActivityIntent(Intent i) {
		i.setAction(BaseActivity.ACTIVITY_TEST_ACTION);
		super.setActivityIntent(i);
	}

}
