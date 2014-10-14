package com.payless.xtify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.payless.R;
import com.payless.ReceiverActivity;
import com.xtify.rn.RichNotificationManger;
import com.xtify.sdk.api.NotificationsPreference;
import com.xtify.sdk.api.XtifyBroadcastReceiver;

public class XtifyNotifier extends XtifyBroadcastReceiver {

	static String TAG = XtifyNotifier.class.getName();
	private static final String NOTIF_ACTION_TYPE = "com.xtify.sdk.NOTIF_ACTION_TYPE";
	private static final String DATA_ACTION = "data.data";
	private static final String DATA_MESSAGE = "data.message";
	private static final String DATA_TITLE = "data.subject";

	@Override
	public void onMessage(Context context, Bundle msgExtras) {

		if (msgExtras.getString(NOTIF_ACTION_TYPE) == null) {
			pushSimpleNotification(context, msgExtras.getString(DATA_TITLE), msgExtras.getString(DATA_MESSAGE), msgExtras.getString(DATA_ACTION));
		}
		RichNotificationManger.processNotifExtras(context, msgExtras);
	}

	@SuppressWarnings("deprecation")
	private void pushSimpleNotification(Context context, String title, String message, String action) {

		int icon = R.drawable.icon;
		NotificationsPreference.setSoundEnabled(context, true);
		NotificationsPreference.setIcon(context, icon);

		CharSequence tickerText = "Payless";

		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		Intent notificationIntent = new Intent(context, ReceiverActivity.class);
		notificationIntent.setAction(Intent.ACTION_VIEW);
		notificationIntent.setData(Uri.parse(action));
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		notification.setLatestEventInfo(context, title, message, contentIntent);

		mNotificationManager.notify(11, notification);
	}

	@Override
	public void onRegistered(Context context) {
		try {
			Log.i(TAG, "OK, SDK registerd");
		} catch (Exception e) {
			Log.d("XtifyNotifier", "Xtify Failed");
		}
	}

	@Override
	public void onC2dmError(Context context, String errorId) {
		Log.i(TAG, "ErrorId: " + errorId);
	}

}
