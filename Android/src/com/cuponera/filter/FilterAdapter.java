package com.cuponera.filter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.R;
import com.cuponera.model.SubCategory;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.ViewProxy;

public class FilterAdapter extends BaseListAdapter {

	private Activity activity;
	private ArrayList<SubCategory> sc;

	public FilterAdapter(Activity activity, ArrayList<SubCategory> sc) {
		this.activity = activity;
		this.sc = sc;
	}

	@Override
	public int getCount() {
		return sc != null ? sc.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_right_filter, false);
		SubCategory s = sc.get(position);
		mViewProxy.findTextView(R.id.filter_text).setText(s.getName());
		if (s.isSelected()) {
			mViewProxy.findTextView(R.id.filter_text).setBackgroundColor(activity.getResources().getColor(R.color.green_strong));
		}
		return mViewProxy.getView();
	}

}
