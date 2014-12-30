package com.cuponera.product;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Product;
import com.cuponera.model.Store;
import com.cuponera.service.images.ImagesRequest;
import com.cuponera.service.images.ImagesResponse;
import com.cuponera.store.StoreBottomFragment;
import com.cuponera.utils.ImageGallery;

public class ProductDescriptionFragment extends BaseFragment {

	private static final String ARGS_PRODUCT = "args_product";
	private static final String ARGS_STORE = "args_store";
	private Product product;
	private Store store;

	@Override
	protected int getLayout() {
		return R.layout.fragment_product_description;
	}

	public static ProductDescriptionFragment newInstance(Product p, Store s) {

		ProductDescriptionFragment fragment = new ProductDescriptionFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();
		b.putParcelable(ARGS_PRODUCT, (Parcelable) p);
		b.putParcelable(ARGS_STORE, (Parcelable) s);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		product = getArguments().getParcelable(ARGS_PRODUCT);
		store = getArguments().getParcelable(ARGS_STORE);
		ImagesRequest request = new ImagesRequest(getBaseActivity()) {

			@Override
			public void onServiceReturned(ImagesResponse response) {
				if (response != null && response.getImages().size() > 0) {
					FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
					transaction.replace(R.id.gallery_adapter, ImageGallery.newInstance(response.getImages()));
					transaction.commit();
				} else {
					mViewProxy.findFrameLayout(R.id.gallery_adapter).setVisibility(View.GONE);
				}
			}
		};
		request.setIdProduct(product.getIdProduct());
		request.execute(false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.store_bottom, StoreBottomFragment.newInstance(store));
		transaction.commit();
		mViewProxy.findTextView(R.id.product_company).setText(store.getName());
		if (product.getIdOffer() > 0) {
			mViewProxy.findTextView(R.id.product_name).setText(product.getoTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getoPrice()));
		} else {
			mViewProxy.findTextView(R.id.product_name).setText(product.getpTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getpPrice()));
		}
		mViewProxy.findTextView(R.id.product_description).setText(product.getpDescription());

	}

}
