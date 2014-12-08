package com.cuponera.product;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.IProduct;
import com.cuponera.utils.Utils;

public class ProductDescriptionFragment extends BaseFragment {

	private static final String ARGS_PRODUCT = "args_id_category";
	private IProduct iProduct;

	@Override
	protected int getLayout() {
		return R.layout.fragment_product_description;
	}

	public static ProductDescriptionFragment newInstance(IProduct p) {

		ProductDescriptionFragment fragment = new ProductDescriptionFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putParcelable(ARGS_PRODUCT, (Parcelable) p);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		iProduct = getArguments().getParcelable(ARGS_PRODUCT);
		Utils.loadImageFromUrl(mViewProxy.findImageView(R.id.product_image), iProduct.getImagePath());
	}

}
