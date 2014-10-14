package com.payless.lookbook;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.payless.BaseActivity;
import com.payless.R;
import com.payless.model.LookBookFilter;
import com.payless.model.LookBookFilterOption;
import com.payless.model.LookBookFilters;
import com.payless.utils.BaseListAdapter;
import com.payless.utils.KeyValueArrayAdapter;
import com.payless.utils.ViewProxy;

public abstract class LookBookFiltersAdapter extends BaseListAdapter {

	private BaseActivity context;
	private List<LookBookFilter> filters;

	public LookBookFiltersAdapter(BaseActivity context) {
		this.context = context;
		LookBookFilters lookBookFilters = context.getSettings().getLookBookFilters();
		if(lookBookFilters != null
				&& lookBookFilters.getFilters() != null) {
			filters = lookBookFilters.getFilters();
		}
		
	}

	@Override
	public int getCount() {
		return filters.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LookBookFilter filter = filters.get(position);
		ViewProxy viewProxy = new ViewProxy(context, R.layout.look_book_filter);
		viewProxy.findTextView(R.id.filterName).setText(filter.getFieldString()+":");
		
		//Spinner
		Spinner spinner = viewProxy.findSpinner(R.id.optionsSpinner);

		spinner.setTag(filter.getField());
		final KeyValueArrayAdapter adapter = new KeyValueArrayAdapter(context, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add(new BasicNameValuePair("",context.getString(R.string.all)));
		for(LookBookFilterOption option : filter.getOptions()){
			adapter.add(new BasicNameValuePair(""+option.getValue(),option.getName()));
		}
		
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String value = adapter.getKey(arg2);
				LookBookFiltersAdapter.this.onItemSelected(arg0.getTag().toString(), value);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		return viewProxy.getView();
	}
	
	public abstract void onItemSelected(String field, String value);

}
