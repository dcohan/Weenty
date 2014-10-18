package com.cuponera.more;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;

public class MoreFragment extends BaseFragment implements HeaderInterface {

	@Override
	protected int getLayout() {
		return 0;
	}

	@Override
	public String getTitle() {
		return getString(R.string.menu_more);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

}
