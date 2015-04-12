package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Product;
import com.cuponera.model.Store;
import com.cuponera.service.product.ProductRequest;
import com.cuponera.utils.Utils;

public class ProductFragment extends BaseFragment {

	private static final String ARGS_STORE = "args_store";
	private ProductAdapter adapter;
	private Store store;
	private ArrayList<Product> products;

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}

	public static ProductFragment newInstance(Store s) {

		ProductFragment fragment = new ProductFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putParcelable(ARGS_STORE, s);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		store = getArguments().getParcelable(ARGS_STORE);
		ProductRequest request = new ProductRequest(getActivity()) {

			@Override
			public void onServiceReturned(com.cuponera.service.product.ProductResponse result) {
				if (result != null) {
					products = result.getProducts();
					fillAdapter();
				}
			}
		};
		request.setIdStore(store.getIdStore());
		request.execute(false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (Utils.isPhone(getActivity())) {
			mViewProxy.findLinearLayout(R.id.description_container).setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null)
			fillAdapter();
	}

	private void fillAdapter() {
		adapter = new ProductAdapter(getBaseActivity(), products, store);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.anim.transition_slide_in_left, R.anim.transition_slide_out_left);
				transaction.replace(Utils.isPhone(getActivity()) ? R.id.container : R.id.description_container,
						ProductDescriptionFragment.newInstance(products.get(position), store));
				if (Utils.isPhone(getActivity())) {
					transaction.addToBackStack(null);
				}
				transaction.commit();
			}
		});
	}
}
