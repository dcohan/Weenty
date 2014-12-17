package com.cuponera.utils;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Images;

public class ImageGallery extends BaseFragment {

	private LinearLayout myGallery;
	private static final String ARGS_IMAGES = "args_images";
	private ArrayList<Images> images;

	@Override
	protected int getLayout() {
		return R.layout.gallery_images;
	}

	public static ImageGallery newInstance(ArrayList<Images> i) {

		ImageGallery fragment = new ImageGallery();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putParcelableArrayList(ARGS_IMAGES, i);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		myGallery = mViewProxy.findLinearLayout(R.id.mygallery);
		images = getArguments().getParcelableArrayList(ARGS_IMAGES);

		for (Images i : images) {
			myGallery.addView(insertPhoto(i.getImagePath()));
		}
	}

	private View insertPhoto(String path) {

		LinearLayout layout = new LinearLayout(getBaseActivity());
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(getBaseActivity());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		Utils.loadImageFromUrl(getActivity(), imageView, path);

		layout.addView(imageView);
		return layout;
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;

	}
}
