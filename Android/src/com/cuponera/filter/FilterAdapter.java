package com.cuponera.filter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.R;
import com.cuponera.model.Store;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.ViewProxy;

public class FilterAdapter extends BaseListAdapter {

	private Activity activity;
	private ArrayList<Store> store;

	public FilterAdapter(Activity activity, ArrayList<Store> store) {
		this.activity = activity;
		this.store = store;
	}

	@Override
	public int getCount() {
		return store != null ? store.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_right_filter, false);
		Store s = store.get(position);
		mViewProxy.findTextView(R.id.filter_text).setText(s.getName());
		return mViewProxy.getView();
	}

}
