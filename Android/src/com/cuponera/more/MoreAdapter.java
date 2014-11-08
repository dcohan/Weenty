package com.cuponera.more;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.model.Category;
import com.cuponera.product.ProductFragment;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.ViewProxy;

public class MoreAdapter extends BaseListAdapter {

	private Activity activity;
	private List<Category> category;

	public MoreAdapter(Activity activity, List<Category> category) {
		this.activity = activity;
		this.category = category;
	}

	@Override
	public int getCount() {
		return category != null ? category.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_more);
		final Category c = category.get(position);
		mViewProxy.findTextView(R.id.more_title).setText(c.getName());

		mViewProxy.getView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = ((BaseActivity) activity).getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductFragment.newInstance(c.getId()));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		return mViewProxy.getView();
	}

}
