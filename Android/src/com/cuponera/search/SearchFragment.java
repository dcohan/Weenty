package com.cuponera.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.model.Category;
import com.cuponera.model.State;
import com.cuponera.model.Store;
import com.cuponera.service.category.CategoryRequest;
import com.cuponera.service.category.CategoryResponse;
import com.cuponera.service.state.StateRequest;
import com.cuponera.service.state.StateResponse;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.settings.Settings;
import com.cuponera.store.StoreAdapter;
import com.cuponera.store.StoreDescriptionFragment;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;

public class SearchFragment extends BaseFragment {

	private ArrayList<Category> category;
	private ArrayList<State> state;
	private ArrayList<Store> store;
	private StoreAdapter adapter;
	private ViewPager viewPager;
	private ViewPager viewPagerState;
	private CustomPagerAdapter customPagerAdapter;
	private CustomPageStateAdapter customPagerStateAdapter;

	@Override
	protected int getLayout() {
		return R.layout.fragment_screen_slide_page;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		category = new ArrayList<Category>();
		state = new ArrayList<State>();
		CategoryRequest request = new CategoryRequest(getActivity()) {

			@Override
			protected void serviceReady(CategoryResponse response) {
				category.addAll(response.getCategory());
				fillCategory();

			}
		};

		if (!request.isResultCached()) {
			request.execute();
		}

		StateRequest stateRequest = new StateRequest(getActivity()) {

			@Override
			public void hideLoading() {
				getBaseActivity().hideLoading();
			}

			@Override
			public void showLoading() {
				getBaseActivity().showLoading();
			}

			@Override
			protected void serviceReady(StateResponse result) {
				if (result != null && result.getState().size() > 0) {
					state.addAll(result.getState());
					State s = new State();
					s.setName("TODAS LAS CIUDADES");
					s.setLatitude(Settings.getInstance(getActivity()).getLatitude());
					s.setLongitude(Settings.getInstance(getActivity()).getLongitude());
					state.add(0, s);
					fillState();

				}
			}
		};
		if (!stateRequest.isResultCached()) {
			stateRequest.execute();
		}
		mViewProxy.findEditText(R.id.search_edit).setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (mViewProxy.findEditText(R.id.search_edit).getText().toString().length() < 3) {
						Toast.makeText(getActivity(), "Ingrese por lo menos 3 letras.", Toast.LENGTH_LONG).show();
						return false;
					}
					Utils.hideKeyboard(getActivity(), mViewProxy.getView());
					search();
					return true;
				}
				return false;
			}
		});
	}

	private void search() {
		SearchRequest request = new SearchRequest(getActivity()) {

			@Override
			public void onServiceReturned(StoreResponse result) {
				if (result != null && result.getStore() != null && result.getStore().size() > 0) {
					store = result.getStore();
					fillAdapter();
				} else {
					mViewProxy.findListView(R.id.product_listview).setAdapter(null);
					EventBus.getInstance().dispatchEvent(new ErrorEvent(0, ErrorHandler.NO_RESULTS_FOUND));
				}
			}
		};
		request.setLatitude(state.get(viewPagerState.getCurrentItem()).getLatitude());
		request.setLongitude(state.get(viewPagerState.getCurrentItem()).getLongitude());
		request.setIdCategory(category.get(viewPager.getCurrentItem()).getId());
		try {
			request.setName(URLEncoder.encode(mViewProxy.findEditText(R.id.search_edit).getText().toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.execute(false);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null) {
			fillAdapter();
		}
		if (customPagerAdapter != null) {
			fillCategory();
		}
	}

	private void fillCategory() {
		viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
		customPagerAdapter = new CustomPagerAdapter(getActivity(), category);
		customPagerAdapter.notifyDataSetChanged();
		viewPager.setAdapter(customPagerAdapter);

	}

	private void fillState() {
		viewPagerState = (ViewPager) getActivity().findViewById(R.id.viewPager_state);
		customPagerStateAdapter = new CustomPageStateAdapter(getActivity(), state);
		customPagerStateAdapter.notifyDataSetChanged();
		viewPagerState.setAdapter(customPagerStateAdapter);

	}

	private void fillAdapter() {
		adapter = new StoreAdapter(getBaseActivity(), store);
		adapter.notifyDataSetChanged();

		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, StoreDescriptionFragment.newInstance(category.get(viewPager.getCurrentItem()).getId(), store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.hideKeyboard(getBaseActivity(), mViewProxy.getView());
	}
}
