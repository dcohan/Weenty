package com.cuponera.product;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.R;
import com.cuponera.model.IProduct;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ViewProxy;

public class ProductAdapter extends BaseListAdapter {

	private Activity activity;
	private List<? extends IProduct> product;

	public ProductAdapter(Activity activity, List<? extends IProduct> product) {
		this.activity = activity;
		this.product = product;
	}

	@Override
	public int getCount() {
		return product != null ? product.size() : 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_product);
		IProduct p = (IProduct)product.get(position);
		Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.product_image), p.getImagePath());
		mViewProxy.findTextView(R.id.product_company).setText(p.getCompany().getName());
		mViewProxy.findTextView(R.id.product_name).setText(p.getDescription());

		return mViewProxy.getView();
	}

}
