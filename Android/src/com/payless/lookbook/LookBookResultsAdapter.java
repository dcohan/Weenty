package com.payless.lookbook;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.payless.BaseActivity;
import com.payless.R;
import com.payless.helpers.AnalyticsHelper;
import com.payless.model.LookBookItem;
import com.payless.utils.BaseListAdapter;
import com.payless.utils.Utils;
import com.payless.utils.ViewProxy;

public class LookBookResultsAdapter extends BaseListAdapter {

	private Context context;
	private List<LookBookItem> results;

	public LookBookResultsAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return getResults().size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy viewProxy = new ViewProxy(context, R.layout.look_book_result);

		final LookBookItem item = results.get(position);

		viewProxy.findTextView(R.id.title).setText(item.getName());
		viewProxy.findTextView(R.id.description).setText(item.getDescription());

		Utils.loadImageFromUrl(viewProxy.findImageView(R.id.image), item.getImageURL());

		viewProxy.getView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.LOOKBOOK_RESULTS, AnalyticsHelper.BUY_NOW, item.getName());
				((BaseActivity) context).openURL(item.getTargetURL(), item.getName());
			}
		});

		return viewProxy.getView();
	}

	public List<LookBookItem> getResults() {
		return results;
	}

	public void setResults(List<LookBookItem> results) {
		this.results = results;
	}

}
