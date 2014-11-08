package com.cuponera.more;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Category;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.service.category.CategoryRequest;
import com.cuponera.service.category.CategoryResponse;

public class MoreFragment extends BaseFragment implements HeaderInterface {

	private ArrayList<Category> category;
	private MoreAdapter adapter;
	private ListView moreList;

	@Override
	protected int getLayout() {
		return R.layout.fragment_more;
	}

	@Override
	public String getTitle() {
		return getString(R.string.menu_more);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		category = new ArrayList<Category>();
		CategoryRequest request = new CategoryRequest(getActivity()) {

			@Override
			public void onServiceReturned(CategoryResponse result) {
				category.addAll(result.getCategory());
				fillAdapter();
			}
		};

		request.execute();

	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null)
			fillAdapter();
	}
	
	private void fillAdapter(){
		moreList = mViewProxy.findListView(R.id.more_listview);
		adapter = new MoreAdapter(getActivity(), category);
		adapter.notifyDataSetChanged();
		moreList.setAdapter(adapter);
	}

}
