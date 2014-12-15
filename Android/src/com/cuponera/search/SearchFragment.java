package com.cuponera.search;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.event.ErrorEvent;
import com.cuponera.event.EventBus;
import com.cuponera.model.Category;
import com.cuponera.model.Store;
import com.cuponera.product.ProductFragment;
import com.cuponera.service.category.CategoryRequest;
import com.cuponera.service.category.CategoryResponse;
import com.cuponera.service.store.StoreResponse;
import com.cuponera.store.StoreAdapter;
import com.cuponera.utils.ErrorHandler;
import com.cuponera.utils.Utils;

public class SearchFragment extends BaseFragment {

	private ArrayList<Category> category;
	private ArrayList<Store> store;
	private StoreAdapter adapter;
	private ViewPager viewPager;
	private CustomPagerAdapter customPagerAdapter;

	@Override
	protected int getLayout() {
		return R.layout.fragment_screen_slide_page;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		category = new ArrayList<Category>();
		CategoryRequest request = new CategoryRequest(getActivity()) {

			@Override
			public void onServiceReturned(CategoryResponse result) {
				category.addAll(result.getCategory());
				fillCategory();
			}
		};

		request.execute();

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewProxy.findEditText(R.id.search_edit).setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
		request.setIdCategory(category.get(viewPager.getCurrentItem()).getId());
		request.setName(mViewProxy.findEditText(R.id.search_edit).getText().toString());
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

	private void fillAdapter() {
		adapter = new StoreAdapter(getBaseActivity(), store);
		adapter.notifyDataSetChanged();

		mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);

		mViewProxy.findListView(R.id.product_listview).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductFragment.newInstance(category.get(viewPager.getCurrentItem()).getId(), store.get(position)));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}
}