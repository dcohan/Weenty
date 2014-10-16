package com.payless.test;

import android.content.Intent;
import android.net.Uri;

import com.payless.ReceiverActivity;
import com.payless.utils.PushHandler;

public class DeepLinkingTest extends BaseTest<ReceiverActivity> {

	public DeepLinkingTest() {
		super(ReceiverActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testDeepLinkIsWorking() {
		
		Intent intent = new Intent(getInstrumentation().getTargetContext(), ReceiverActivity.class);
		Uri uri = Uri.parse("payless://" + PushHandler.PROFILE);
		intent.setData(uri);
		intent.setAction(Intent.ACTION_VIEW);
		setActivityIntent(intent);
		mActivity = getActivity();
		assertNotNull(mActivity);
		
		Intent newIntent= mActivity.newIntent;
		assertEquals(newIntent.getData(), mActivity.getIntent().getData());
		assertEquals(newIntent.getAction(), mActivity.getIntent().getAction());
	}

}
