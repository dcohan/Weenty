package com.cuponera.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KeyValueArrayAdapter extends ArrayAdapter<String> {

	private List<String> keys = new ArrayList<String>();
	private List<String> values = new ArrayList<String>();
	
	public KeyValueArrayAdapter(final Context context, final int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final TextView view = (TextView) super.getView(position, convertView, parent);

		view.setText(getItem(position));
		return view;
	}

	@Override
	public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
		final TextView view = (TextView) super.getDropDownView(position, convertView, parent);

		view.setText(getItem(position));
		return view;
	}

	public void setKeyValue(List<String> keys, List<String> vaules) {
		if (keys.size() != vaules.size()) {
			throw new RuntimeException("The length of keys and values is not in agreement.");
		}

		final int N = keys.size();
		for (int i = 0; i < N; i++) {
			add(vaules.get(i));
		}
	}
	
	public void add(NameValuePair nameValuePair) {
		keys.add(nameValuePair.getName());
		values.add(nameValuePair.getValue());
		add(nameValuePair.getValue());
	}

	public void setValues(List<String> values) {
		this.values = values;
		if (keys.size() > 0) {
			setKeyValue(keys, values);
		}
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
		if (values.size() > 0) {
			setKeyValue(keys, values);
		}
	}

	public String getValue(final int position) {
		return getItem(position);
	}

	public String getKey(final int position) {
		return keys.get(position);
	}

}