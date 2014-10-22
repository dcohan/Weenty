package com.cuponera.stores;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Item;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.service.stores.StoresRequest;
import com.cuponera.service.stores.StoresResponse;
import com.cuponera.utils.EndlessScrollListener;

public class StoreFinderSearchViewFragment extends BaseFragment implements HeaderInterface {

	private static final String ARGS_RESPONSE = "args_response";
	private static final String ARGS_REQUEST = "args_request";
	private ArrayList<Item> result;
	private StoreFinderAdapter storeAdapter;

	public static StoreFinderSearchViewFragment newInstance(ArrayList<Item> result, ArrayList<StoresRequest> requestParams) {

		StoreFinderSearchViewFragment fragment = new StoreFinderSearchViewFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putParcelableArrayList(ARGS_REQUEST, requestParams);
		b.putParcelableArrayList(ARGS_RESPONSE, result);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public String getTitle() {
		return getString(R.string.stores_title);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_store_search;
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		final ListView mListView = mViewProxy.findListView(R.id.store_search_listview);
		result = getArguments().getParcelableArrayList(ARGS_RESPONSE);
		
		storeAdapter = new StoreFinderAdapter(getActivity());
		storeAdapter.setResults(result);
		
		mListView.setAdapter(storeAdapter);

		mListView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				StoresRequest request = new StoresRequest(getActivity()) {

					@Override
					public void loadWasCancelled() {

					}

					@Override
					public void loadFailed() {

					}
					
					@Override
					public void showLoading() {
					}

					@Override
					public void onServiceReturned(StoresResponse result) {
						if (result.succes()) {
							storeAdapter.getResults().addAll(result.getItems());
							storeAdapter.notifyDataSetChanged();
							mListView.invalidateViews();
						}

					}
				};
				ArrayList<StoresRequest> r = new ArrayList<StoresRequest>();
				r = getArguments().getParcelableArrayList(ARGS_REQUEST);
				request.setCityName(r.get(0).getCityName());
				request.setLatitude(r.get(0).getLatitude());
				request.setLongitud(r.get(0).getLongitud());
				request.setZipCode(r.get(0).getZipCode());
				request.setState(r.get(0).getState());
				request.setPage(page);
				request.execute();

			}
		});

	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	};
}
