package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.analytics.AnalyticsHelpers;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.filter.FilterAdapter;
import com.cuponera.map.GoogleMapFragment;
import com.cuponera.model.Store;
import com.cuponera.model.SubCategory;
import com.cuponera.service.offer.OfferRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.store.StoreAdapter;
import com.cuponera.store.StoreDescriptionFragment;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ValidationUtils;

public class OfferFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_store;
	}

	private ArrayList<Store> store;
	private ArrayList<SubCategory> sc;
	private StoreAdapter adapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private FilterAdapter filterAdapter;
	private ArrayList<Store> filteredArrayToShow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		OfferRequest request = new OfferRequest(getActivity()) {

			@Override
			public void onServiceReturned(StoreResponse result) {
				if (result != null) {
					if (result.getStore().size() > 0) {
						store = result.getStore();
						fillAdapter();
						setFilters();
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
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, StoreDescriptionFragment.newInstance(store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}

	private void setFilters() {
		Utils.setCalibri(getActivity(), mViewProxy.findTextView(R.id.filter_category));
		mDrawerLayout = (DrawerLayout) mViewProxy.findView(R.id.drawer_layout);
		mDrawerList = (ListView) mViewProxy.findListView(R.id.right_drawer);
		sc = new ArrayList<SubCategory>();

		for (int i = 0; i < store.size(); i++) {
			for (int j = 0; j < store.get(i).getCategory().size(); j++) {
				for (int k = 0; k < store.get(i).getCategory().get(j).getSubCategory().size(); k++) {
					if (!ValidationUtils.isNullOrEmpty(store.get(i).getCategory().get(j).getSubCategory().get(k).getName())) {
						SubCategory subObject = new SubCategory();

						subObject.setId(store.get(i).getCategory().get(j).getSubCategory().get(k).getId());
						subObject.setName(store.get(i).getCategory().get(j).getSubCategory().get(k).getName());
						ArrayList<Integer> idSt = new ArrayList<Integer>();
						idSt.add(store.get(i).getIdStore());
						subObject.setIdStores(idSt);
						sc.add(subObject);
					}
				}

			}
		}

		filterAdapter = new FilterAdapter(getActivity(), sc);
		mDrawerList.setAdapter(filterAdapter);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!sc.get(position).isSelected()) {
					sc.get(position).setSelected(true);
					view.setBackgroundColor(getResources().getColor(R.color.green_strong));
				} else {
					sc.get(position).setSelected(false);
					view.setBackgroundColor(getResources().getColor(R.color.white));
				}
				ArrayList<Integer> toFilter = new ArrayList<Integer>();

				for (SubCategory subcategory : sc) {
					if (subcategory.isSelected()) {
						toFilter.addAll(subcategory.getIdStores());
					}
				}
				if (toFilter.size() > 0) {
					getSetFilters(toFilter);
				} else {
					adapter = new StoreAdapter(getBaseActivity(), store);
					adapter.notifyDataSetChanged();
					mViewProxy.findListView(R.id.store_listview).setAdapter(adapter);
				}
			}
		});

		mViewProxy.findTextView(R.id.filter_category).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}

			}
		});
	}

	private synchronized void getSetFilters(ArrayList<Integer> idFilter) {

		filteredArrayToShow = new ArrayList<Store>();
		for (int i = 0; i < store.size(); i++) {
			for (Integer idStore : idFilter) {
				if (idStore == store.get(i).getIdStore()) {
					filteredArrayToShow.add(store.get(i));
				}
			}
		}
		adapter = new StoreAdapter(getBaseActivity(), filteredArrayToShow);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.store_listview).setAdapter(adapter);

	}
}
