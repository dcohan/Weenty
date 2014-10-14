package com.payless.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public abstract class DrawableDownloadTask extends AsyncTask<String, Void, Bitmap> {
	Context context;

	public DrawableDownloadTask(Context context) {
		this.context = context;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
			onPostFailed();
			cancel(true);
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		onPostFinish(new BitmapDrawable(context.getResources(), result));
	}
	
	public abstract void onPostFinish(Drawable drawable);
	public abstract void onPostFailed();
	
}