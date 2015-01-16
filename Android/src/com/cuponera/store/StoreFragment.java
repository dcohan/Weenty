package com.cuponera.store;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.cuponera.service.store.StoreRequest;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ValidationUtils;

public class StoreFragment extends BaseFragment {

	private static final String ARGS_ID_CATEGORY = "args_id_category";
	private static final String ARGS_NAME_CATEGORY = "args_name_category";
	private ArrayList<Store> store;
	private ArrayList<SubCategory> sc;
	private StoreAdapter adapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private FilterAdapter filterAdapter;
	private ArrayList<Store> filteredArrayToShow;

	@Override
	protected int getLayout() {
		return R.layout.fragment_store;
	}

	public static StoreFragment newInstance(int idCategory, String categoryName) {

		StoreFragment fragment = new StoreFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putInt(ARGS_ID_CATEGORY, idCategory);
		b.putString(ARGS_NAME_CATEGORY, categoryName);

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

					setFilters();

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
		if (adapter != null) {
			fillAdapter();
		}
		if (filterAdapter != null) {
			setFilters();
		}
	}

	private void fillAdapter() {
		mViewProxy.findTextView(R.id.store_category).setText(getArguments().getString(ARGS_NAME_CATEGORY));
		Utils.setCalibri(getActivity(), mViewProxy.findTextView(R.id.store_category));
		mViewProxy.findImageView(R.id.cx).setVisibility(View.VISIBLE);
		mViewProxy.findImageView(R.id.cx).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getBaseActivity().openURL("http://www.redcx.com/");

			}
		});

		mViewProxy.findImageView(R.id.location_stores_image).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelpers.getInstance().logScreen(AnalyticsHelpers.MAP_ALL);
				getBaseActivity().pushFragment(GoogleMapFragment.newInstance(store), true);
			}
		});

		adapter = new StoreAdapter(getBaseActivity(), store);
		adapter.notifyDataSetChanged();
		mViewProxy.findListView(R.id.store_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.store_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.anim.transition_slide_in_left, R.anim.transition_slide_out_left);
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
						boolean added = true;
						SubCategory subObject = new SubCategory();

						subObject.setId(store.get(i).getCategory().get(j).getSubCategory().get(k).getId());
						subObject.setName(store.get(i).getCategory().get(j).getSubCategory().get(k).getName());
						ArrayList<Integer> idSt = new ArrayList<Integer>();
						idSt.add(store.get(i).getIdStore());
						subObject.setIdStores(idSt);
						for (SubCategory subca : sc) {
							if (subca.getName().equalsIgnoreCase(store.get(i).getCategory().get(j).getSubCategory().get(k).getName())) {
								added = false;
								break;
							}

						}
						if (added) {
							sc.add(subObject);
						}
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

		mViewProxy.findListView(R.id.store_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.anim.transition_slide_in_left, R.anim.transition_slide_out_left);
				transaction.replace(R.id.container, StoreDescriptionFragment.newInstance(filteredArrayToShow.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

	}
}
