package com.cuponera.event;

public class LoaderEvent extends BaseEvent {

	private int mCustomViewId;// = R.id.loading;

	public final static int ACTION_NEW = 0;
	public final static int ACTION_START = 1;
	public final static int ACTION_FINISH = 2;

	private int mAction = ACTION_NEW;

	// }

	public LoaderEvent(int action, int viewId) {
		super(LoaderEvent.class);
		mAction = action;
		mCustomViewId = viewId;
	}

	public int getCustomViewId() {
		return mCustomViewId;
	}

	public int getAction() {
		return mAction;
	}
}
