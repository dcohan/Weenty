package com.cuponera.store;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.R;
import com.cuponera.model.Store;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ViewProxy;

public class StoreAdapter extends BaseListAdapter {

	private Activity activity;
	private ArrayList<Store> store;

	public StoreAdapter(Activity activity, ArrayList<Store> store) {
		this.activity = activity;
		this.store = store;
	}

	@Override
	public int getCount() {
		return store != null ? store.size() : 1;
	}

	public Object getItem(int position) {
		return store.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_store, false);
		Store s = store.get(position);
		if (!s.hasOffers()) {
			mViewProxy.findView(R.id.offer_circle).setVisibility(View.GONE);
		}
		Utils.loadImageFromUrl(activity, mViewProxy.findImageView(R.id.store_image), s.getImagePath());
		mViewProxy.findTextView(R.id.store_name).setText(s.getName());
		Utils.setCalibri(activity, mViewProxy.findTextView(R.id.store_name));
		mViewProxy.findTextView(R.id.store_address).setText(s.getAddress());
		mViewProxy.findTextView(R.id.store_distance).setText(String.format("%.2f", s.getDistance()) + "km");
		return mViewProxy.getView();
	}
}
