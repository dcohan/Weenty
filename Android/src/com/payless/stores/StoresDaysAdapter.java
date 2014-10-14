package com.payless.stores;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.payless.R;
import com.payless.model.StoreHours;
import com.payless.utils.BaseListAdapter;
import com.payless.utils.ViewProxy;

public class StoresDaysAdapter extends BaseListAdapter {
	private Activity activity;
	private ArrayList<StoreHours> storeHours;

	public StoresDaysAdapter(Activity activity, ArrayList<StoreHours> storeHours) {
		this.activity = activity;
		this.storeHours = storeHours;
	}

	@Override
	public int getCount() {
		return storeHours.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_store_days);
		StoreHours store = storeHours.get(position);
		mViewProxy.findTextView(R.id.store_date).setText(setCompleteDay(store));
		mViewProxy.findTextView(R.id.store_hours).setText(store.getTiming().getOpen() + " - " + store.getTiming().getClose());
		return mViewProxy.getView();
	}

	private String setCompleteDay(StoreHours store) {
		String day = store.getDay();
		if (day.equalsIgnoreCase("sat")) {
			return "Saturday";
		} else if (day.equalsIgnoreCase("sun")) {
			return "Sunday";
		} else if (day.equalsIgnoreCase("mon")) {
			return "Monday";

		} else if (day.equalsIgnoreCase("tue")) {
			return "Tuesday";
		} else if (day.equalsIgnoreCase("wed")) {
			return "Wednesday";
		} else if (day.equalsIgnoreCase("thu")) {
			return "Thursday";
		} else if (day.equalsIgnoreCase("fri")) {
			return "Friday";
		}
		return "";
	}

}
