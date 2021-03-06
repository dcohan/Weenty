package com.cuponera;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.navigation.HeaderInterface;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ViewProxy;

public abstract class BaseFragment extends Fragment {

	protected ViewProxy mViewProxy;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			if (Utils.isPhone(getActivity())) {
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		mViewProxy = new ViewProxy(getActivity(), getLayout());

		if (this instanceof HeaderInterface) {
			getBaseActivity().navBarFragment.setTitle(((HeaderInterface) BaseFragment.this).getTitle());
		}

		return mViewProxy.getView();

	}

	protected abstract int getLayout();

	protected BaseActivity getBaseActivity() {
		return (BaseActivity) getActivity();
	}

	protected boolean showHomeButton() {
		return true;
	}

}
