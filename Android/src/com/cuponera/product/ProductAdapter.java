package com.cuponera.product;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.R;
import com.cuponera.model.Product;
import com.cuponera.model.Store;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ViewProxy;

public class ProductAdapter extends BaseListAdapter {

	private Activity activity;
	private Store store;
	private List<Product> product;

	public ProductAdapter(Activity activity, List<Product> product, Store s) {
		this.activity = activity;
		this.product = product;
		store = s;
	}

	@Override
	public int getCount() {
		return product != null ? product.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_product, false);
		final Product p = product.get(position);
		Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.product_image), p.getpImagePath());
		mViewProxy.findTextView(R.id.product_company).setText(store.getName());
		mViewProxy.findTextView(R.id.product_name).setText(p.getpTitle());
		return mViewProxy.getView();
	}

}
