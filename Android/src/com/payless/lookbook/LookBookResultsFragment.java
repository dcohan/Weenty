package com.payless.lookbook;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.payless.BaseFragment;
import com.payless.R;
import com.payless.model.LookBookItem;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.lookbook.LookBookListRequest;
import com.payless.service.lookbook.LookBookListResponse;
import com.payless.utils.EndlessScrollListener;

public class LookBookResultsFragment extends BaseFragment implements HeaderInterface {
	
	private final static String RESPONSE = "response";
	private final static String FILTERS = "filters";
	
	private LookBookResultsAdapter adapter;
	private ListView mListView;
	
	public static LookBookResultsFragment newInstance(LookBookListResponse response, ArrayList<String> filters){
		LookBookResultsFragment fragment = new LookBookResultsFragment();
		Bundle b = new Bundle();
		b.putParcelable(RESPONSE, response);
		b.putStringArrayList(FILTERS, filters);
		fragment.setArguments(b);
		
		return fragment;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_lookbook_results;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		LookBookListResponse response = getArguments().getParcelable(RESPONSE);
		List<LookBookItem> list = response.getItems();
		
		adapter = new LookBookResultsAdapter(getBaseActivity());
		adapter.setResults(list);
		
		mListView = mViewProxy.findListView(R.id.listResults);
		mListView.setAdapter(adapter);
		
		mListView.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				
				LookBookListRequest request = new LookBookListRequest(getBaseActivity()) {
					
					@Override
					public void loadFailed() {
					}
					
					@Override
					public void onServiceReturned(LookBookListResponse result) {
						if(result.succes()){
							adapter.getResults().addAll(result.getItems());
							adapter.notifyDataSetChanged();
							mListView.invalidateViews();
						}
					}
					
					@Override
					public void showLoading() {
					}
					
					@Override
					public void hideLoading() {
					}
				};
				
				request.setPage(page);
				request.setFilters(getArguments().getStringArrayList(FILTERS));
				
				request.execute();
				
			}
		});
		
	}

	@Override
	public String getTitle() {
		return getString(R.string.look_book_results_title);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
