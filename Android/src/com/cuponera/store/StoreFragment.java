package com.cuponera.store;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.model.Store;
import com.cuponera.product.ProductFragment;
import com.cuponera.service.store.StoreRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.utils.ErrorHandler;

public class StoreFragment extends BaseFragment {

	private static final String ARGS_ID_CATEGORY = "args_id_category";
	private ArrayList<Store> store;
	private StoreAdapter adapter;

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
				if (result != null && result.getStore() != null && result.getStore().size() > 0) {
					store = result.getStore();
					fillAdapter();
				} else {
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_RESULTS_FOUND));
					getBaseActivity().onHomeButton();
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
		mViewProxy.findImageView(R.id.cx).setVisibility(View.VISIBLE);
		mViewProxy.findImageView(R.id.cx).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getBaseActivity().openURL("http://www.redcx.com/");

			}
		});
		adapter = new StoreAdapter(getBaseActivity(), store);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductFragment.newInstance(getArguments().getInt(ARGS_ID_CATEGORY), store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}

}
