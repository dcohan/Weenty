package com.cuponera.navigation;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.settings.Settings;

public class MenuFragment extends BaseFragment {

	private boolean isOpen = true;
	private boolean animating = false;
	private boolean enabled = true;
	private MenuInterface delegate;

	@Override
	protected int getLayout() {
		return R.layout.fragment_menu;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewProxy.setVisibility(R.id.menuButtonAdmin, Settings.getInstance(getActivity()).AdminAllowed());
		setActions();
		hide();
	}

	public void toggle() {
		mViewProxy.setVisibility(R.id.menuButtonAdmin, Settings.getInstance(getActivity()).AdminAllowed());
		if (enabled && !animating) {
			if (isOpen()) {
				delegate.onChangeState(false);
				Animation animationOut = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_animation_out);
				animationOut.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						show();
						animating = true;
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						hide();
					}
				});

				mViewProxy.getView().startAnimation(animationOut);
			} else {
				delegate.onChangeState(true);
				Animation animationIn = AnimationUtils.loadAnimation(getActivity(), R.anim.menu_animation_in);
				animationIn.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						hide();
						animating = true;
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						show();
					}
				});
				mViewProxy.getView().startAnimation(animationIn);

			}
		}

	}

	public void open() {
		if (!isOpen()) {
			toggle();
		}
	}

	public void close() {
		if (isOpen()) {
			toggle();
		}
	}

	public void hide() {
		mViewProxy.getView().setVisibility(View.INVISIBLE);
		isOpen = false;
		animating = false;
	}

	public void show() {
		mViewProxy.getView().setVisibility(View.VISIBLE);
		isOpen = true;
		animating = false;
	}

	public boolean isOpen() {
		return isOpen;
	}

	private void setActions() {
		mViewProxy.findButton(R.id.menuButtonHome).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onHomeButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonAdmin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onAdminButton();
			}
		});

		mViewProxy.findButton(R.id.menuOfertas).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onSpecialOffers();
			}
		});

		mViewProxy.findButton(R.id.menuButtonMore).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onMoreButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonSearch).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onSearchButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonSettings).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onPreferenceButton();
			}
		});

	}

	public MenuInterface getDelegate() {
		return delegate;
	}

	public void setDelegate(MenuInterface delegate) {
		this.delegate = delegate;
	}

	public void enable() {
		enabled = false;
	}

	public void disable() {
		enabled = false;
	}

	public interface MenuInterface {

		public void onChangeState(boolean open);

		public void onHomeButton();

		public void onAdminButton();

		public void onSpecialOffers();

		public void onMoreButton();

		public void onSearchButton();

		public void onPreferenceButton();

	}

}
