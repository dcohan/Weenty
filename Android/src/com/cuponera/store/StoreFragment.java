package com.cuponera.store;

import android.os.Bundle;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.store.StoreRequest;
import com.cuponera.service.store.StoreResponse;

public class StoreFragment extends BaseFragment {

	private static final String ARGS_ID_CATEGORY = "args_id_category";

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}

	public static StoreFragment newInstance(int idCategory) {

		StoreFragment fragment = new StoreFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putInt(ARGS_ID_CATEGORY, idCategory);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StoreRequest request = new StoreRequest(getActivity()) {

			@Override
			public void onServiceReturned(StoreResponse result) {
				if (result != null) {
				}
			}
		};
		request.setIdCategory(getArguments().getInt(ARGS_ID_CATEGORY));
		request.execute(false);
	}

}
