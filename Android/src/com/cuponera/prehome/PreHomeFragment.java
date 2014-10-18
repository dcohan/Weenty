package com.cuponera.prehome;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.androidquery.AQuery;
import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.PreHome;
import com.cuponera.utils.DrawableDownloadTask;
import com.cuponera.utils.Utils;

public class PreHomeFragment extends BaseFragment {

	private final int PRE_HOME_DURATION = 3000;

	private PreHome preHomeInfo;
	private boolean readyToShow;

	public static PreHomeFragment newInstance() {
		return new PreHomeFragment();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_pre_home;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		runHome();
		
		//reload();
	}

	public void reload() {
		if (preHomeInfo == null) {
			preHomeInfo = getBaseActivity().getSettings().getPreHomeInfo();
			if (preHomeInfo != null) {

				final AQuery aq = new AQuery(mViewProxy.findImageView(R.id.image));
				Bitmap bitmap = aq.getCachedImage(preHomeInfo.getImageURL());
				if(bitmap == null) {
					new DrawableDownloadTask(getActivity()) {
						
						@Override
						public void onPostFinish(Drawable drawable) {
							aq.image(drawable);
							mViewProxy.findImageView(R.id.image).setImageDrawable(drawable);
							mViewProxy.findImageView(R.id.image).setScaleType(ScaleType.FIT_XY);
						}
						
						@Override
						public void onPostFailed() {}
					}.execute(preHomeInfo.getImageURL());
				} else {
					mViewProxy.findImageView(R.id.image).setImageBitmap(bitmap);
					mViewProxy.findImageView(R.id.image).setScaleType(ScaleType.FIT_XY);
				}

				Handler handler = new Handler();

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (readyToShow) {
							runHome();
						} else {
							readyToShow = true;
						}

					}
				}, PRE_HOME_DURATION);
			} else {
				readyToShow = true;
			}
		} else if (readyToShow) {
			runHome();
		} else {
			readyToShow = true;
		}
	}

	public void runHome() {
		if (Utils.hasInternetConnection(getBaseActivity())) {
			getBaseActivity().runHomeActivity();
		}
	}

}
