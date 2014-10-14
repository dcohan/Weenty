package com.payless.test;

import com.payless.prehome.PreHomeActivity;

public class PreHomeActivityTest extends BaseTest<PreHomeActivity> {
	
	public PreHomeActivityTest() {
		super(PreHomeActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
	}
	
	public void testPreHome() {
		assertNotNull(mActivity);
	}
	
//	public void testFirstRun() {
//		mActivity.getSettings().getProfile().setFirstRun(true);
//		assertNotNull(mActivity);
//	}
	
}
