package com.payless;

import android.content.Intent;
import android.os.Bundle;

import com.payless.prehome.PreHomeActivity;

public class ReceiverActivity extends BaseActivity {

	public Intent newIntent;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		newIntent = new Intent(ReceiverActivity.this, PreHomeActivity.class);
		if (isValidPushData()) {
			newIntent.setAction(getIntent().getAction());
			newIntent.setData(getIntent().getData());
		}
		startActivity(newIntent);
		finish();
	}

}
