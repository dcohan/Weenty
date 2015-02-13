package com.cuponera.more;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Category;
import com.cuponera.navigation.HeaderImageInterface;
import com.cuponera.navigation.HeaderInterface;
import com.cuponera.service.category.CategoryRequest;
import com.cuponera.service.category.CategoryResponse;
import com.cuponera.utils.Utils;

public class MoreFragment extends BaseFragment implements HeaderInterface {

	private ArrayList<Category> category;
	private ArrayList<Category> categoryExtended;
	private MoreAdapter adapter;
	private ListView moreList;
	private ListView moreListExtended;

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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		category = new ArrayList<Category>();
		CategoryRequest request = new CategoryRequest(getActivity()) {

			@Override
			protected void serviceReady(CategoryResponse response) {
				category.addAll(response.getCategory());
				fillAdapter();

			}
		};
		if (!request.isResultCached()) {
			request.execute();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adapter != null)
			if (Utils.isPhone(getActivity()))
				fillAdapter();
	}

	private void fillAdapter() {
		moreList = mViewProxy.findListView(R.id.more_listview);
		if (Utils.isTablet(getActivity())) {
			moreListExtended = mViewProxy.findListView(R.id.more_listview_2);
			categoryExtended = new ArrayList<Category>();
			int categoryExtendedSize = category.size() / 2;

			for (int i = categoryExtendedSize; i < category.size(); i++) {
				categoryExtended.add(category.get(i));
			}
			category.removeAll(categoryExtended);

			adapter = new MoreAdapter(getActivity(), categoryExtended);
			adapter.notifyDataSetChanged();
			moreListExtended.setAdapter(adapter);
		}

		adapter = new MoreAdapter(getActivity(), category);

		adapter.notifyDataSetChanged();
		moreList.setAdapter(adapter);
	}

}
