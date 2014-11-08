package com.cuponera.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.product.ProductRequest;

public class ProductFragment extends BaseFragment {
	
	 private static final String ARGS_ID_CATEGORY = "args_id_category";

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}
	
	public static ProductFragment newInstance(int idCategory) {
	
		  ProductFragment fragment = new ProductFragment();
		  Bundle b = fragment.getArguments();
		  if (b == null)
		   b = new Bundle();
		  b.putInt(ARGS_ID_CATEGORY, idCategory);
	
		  fragment.setArguments(b);
		  return fragment;	
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

		request.setIdCategory(getArguments().getInt(ARGS_ID_CATEGORY));
		request.execute(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
