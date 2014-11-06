package com.cuponera.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.product.ProductRequest;

public class ProductFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ProductRequest request = new ProductRequest(getActivity()) {

			@Override
			public void onServiceReturned(com.cuponera.service.product.ProductResponse result) {
				if (result != null) {
					ProductAdapter adapter = new ProductAdapter(getBaseActivity(), result.getProducts());
					mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);
				}
			}
		};

		request.execute();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
