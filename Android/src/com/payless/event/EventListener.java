package com.payless.event;

import android.os.Handler;
 
public abstract class EventListener<T extends BaseEvent> extends Handler {

	public abstract void onEvent(T event);

	public void dipatchEvent(final BaseEvent event) {

		post(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				onEvent((T) event);
			}

		});
	}

}