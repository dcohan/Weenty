package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.model.Store;
import com.cuponera.service.offer.OfferRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.store.StoreAdapter;
import com.cuponera.utils.ErrorHandler;

public class OfferFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}

	private ArrayList<Store> store;
	private StoreAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		OfferRequest request = new OfferRequest(getActivity()) {

			@Override
			public void onServiceReturned(StoreResponse result) {
				if (result != null) {
					store = result.getStore();
					fillAdapter();
				} else {
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.SYSTEM_SERVER_ERROR));

				}
			}
		};

		request.execute(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null)
			fillAdapter();
	}

	private void fillAdapter() {
		adapter = new StoreAdapter(getBaseActivity(), store);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductFragment.newInstance(store.get(position).getIdCategory(), store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}
}
