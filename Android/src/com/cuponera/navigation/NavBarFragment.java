package com.cuponera.navigation;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.MenuFragment.MenuInterface;
import com.cuponera.utils.Utils;

public class NavBarFragment extends BaseFragment implements MenuInterface {

	private MenuFragment menu;
	private ImageView menuButton;
	private ImageView auxImage;
	private FrameLayout menuOutside;

	@Override
	protected int getLayout() {
		return R.layout.fragment_header;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		auxImage = mViewProxy.findImageView(R.id.auxiliarImage);

		menu = (MenuFragment) getBaseActivity().getSupportFragmentManager().findFragmentById(R.id.menuFragment);

		menu.setDelegate(this);

		mViewProxy.findImageView(R.id.homeButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.hideKeyboard(getActivity(), getView());
				getBaseActivity().onHomeButton();
			}
		});

		menuButton = mViewProxy.findImageView(R.id.menuButton);
		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getMenu().toggle();
			}
		});

		menuOutside = mViewProxy.findFrameLayout(R.id.menuOutside);
		menuOutside.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getMenu().close();
			}
		});

	}

	public void setTitle(String title) {
		mViewProxy.findTextView(R.id.headerTitle).setText(title);
	}

	public void setRightImage(final HeaderImageInterface image) {
		if (image != null) {
			auxImage.setVisibility(View.VISIBLE);
			auxImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					image.onClickListener();
				}
			});
			auxImage.setImageDrawable(image.getDrawable());
		} else {
			auxImage.setVisibility(View.GONE);
		}
	}

	public MenuFragment getMenu() {
		return menu;
	}

	public void setMenu(MenuFragment menu) {
		this.menu = menu;
	}

	public void show() {
		mViewProxy.getView().setVisibility(View.VISIBLE);
	}

	public void hide() {
		mViewProxy.getView().setVisibility(View.GONE);
	}

	@Override
	public void onChangeState(boolean open) {
		menuButton.setActivated(open);
		if (open) {
			menuOutside.setVisibility(View.VISIBLE);
		} else {
			menuOutside.setVisibility(View.GONE);
		}
	}

	@Override
	public void onHomeButton() {
		getBaseActivity().onHomeButton();
	}

	@Override
	public void onAdminButton() {
		getBaseActivity().onAdminButton();
	}

	@Override
	public void onSpecialOffers() {
		getBaseActivity().onSpecialOffers();

	}

	@Override
	public void onGastronomic() {
		getBaseActivity().onGastronomic();
	}

	@Override
	public void onStoresButton() {
		getBaseActivity().onStoresButton();
	}

	@Override
	public void onMoreButton() {
		getBaseActivity().onMoreButton();
	}

	@Override
	public void onPreferenceButton() {
		getBaseActivity().onPreferenceButton();
		
	}
	
	public void showHomeButton(boolean show) {
		mViewProxy.findImageView(R.id.homeButton).setVisibility((show) ? View.VISIBLE : View.GONE);
	}



}
