package com.cuponera.search;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Category;
import com.cuponera.service.category.CategoryRequest;
import com.cuponera.service.category.CategoryResponse;

public class SearchFragment extends BaseFragment {

	private ArrayList<Category> category;

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
				ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
				CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity(), category);
				viewPager.setAdapter(customPagerAdapter);
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
		            return true;
		        }
		        return false;
		    }
		});
	}

}
