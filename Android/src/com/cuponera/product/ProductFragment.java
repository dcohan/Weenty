package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.IProduct;
import com.cuponera.service.product.ProductRequest;

public class ProductFragment extends BaseFragment {

	private static final String ARGS_ID_CATEGORY = "args_id_category";
	private ProductAdapter adapter;
	private ArrayList<? extends IProduct> products;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ProductRequest request = new ProductRequest(getActivity()) {

			@Override
			public void onServiceReturned(com.cuponera.service.product.ProductResponse result) {
				if (result != null) {
					products = result.getProducts();
					fillAdapter();
				}
			}
		};
		request.setIdCategory(getArguments().getInt(ARGS_ID_CATEGORY));
		request.execute(false);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null)
			fillAdapter();
	}

	private void fillAdapter() {
		adapter = new ProductAdapter(getBaseActivity(), products);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductDescriptionFragment.newInstance(products.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}

}
