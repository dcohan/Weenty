package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.analytics.AnalyticsHelpers;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.map.GoogleMapFragment;
import com.cuponera.model.Category;
import com.cuponera.model.Store;
import com.cuponera.service.offer.OfferRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.store.StoreAdapter;
import com.cuponera.store.StoreDescriptionFragment;
import com.cuponera.utils.ErrorHandler;

public class OfferFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_store;
	}

	private ArrayList<Store> store;
	private StoreAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		OfferRequest request = new OfferRequest(getActivity()) {

			@Override
			public void onServiceReturned(StoreResponse result) {
				if (result != null) {
					if (result.getStore().size() > 0) {
						store = result.getStore();
						fillAdapter();
					} else {
						EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_RESULTS_FOUND));
						getBaseActivity().onHomeButton();
					}
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
		mViewProxy.findListView(R.id.store_listview).setAdapter(adapter);
		mViewProxy.findTextView(R.id.store_category).setText(getResources().getString(R.string.menu_highlighted));
		mViewProxy.findImageView(R.id.location_stores_image).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelpers.getInstance().logScreen(AnalyticsHelpers.MAP_ALL);
				getBaseActivity().pushFragment(GoogleMapFragment.newInstance(store), true);
			}
		});

		mViewProxy.findListView(R.id.store_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int category = 0;
				for (Category c : store.get(position).getCategory()) {
					if (c.getId() > 0) {
						category = c.getId();
						break;
					}

				}
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, StoreDescriptionFragment.newInstance(category, store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}
}
