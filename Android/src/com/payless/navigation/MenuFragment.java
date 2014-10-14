package com.payless.navigation;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.cuponera.R;
import com.payless.BaseFragment;

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
		setActions();
		hide();
	}

	public void toggle() {
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

		mViewProxy.findButton(R.id.menuButtonShop).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onShopButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonCoupons).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onMyCouponsButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonLookBook).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onLookBookButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonSocial).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onSocialButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonStores).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onStoresButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonProfile).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onProfileButton();
			}
		});

		mViewProxy.findButton(R.id.menuButtonSettings).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDelegate().onSettingsButton();
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

		public void onShopButton();

		public void onMyCouponsButton();

		public void onLookBookButton();

		public void onSocialButton();

		public void onStoresButton();

		public void onProfileButton();

		public void onSettingsButton();
	}

}
