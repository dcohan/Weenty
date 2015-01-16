package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Images;
import com.cuponera.model.Product;
import com.cuponera.model.Store;
import com.cuponera.service.images.ImagesRequest;
import com.cuponera.service.images.ImagesResponse;
import com.cuponera.store.StoreBottomFragment;
import com.cuponera.utils.ImageGallery;
import com.cuponera.utils.ValidationUtils;

public class ProductDescriptionFragment extends BaseFragment {

	private static final String ARGS_PRODUCT = "args_product";
	private static final String ARGS_STORE = "args_store";
	private Product product;
	private Store store;
	private ArrayList<Images> images = new ArrayList<Images>();

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

		Images i = new Images();

		if (isOffer()) {
			if (!ValidationUtils.isNullOrEmpty(product.getoImagePath())) {
				i.setImagePath(product.getoImagePath());
				images.add(i);
			}
		} else {
			if (!ValidationUtils.isNullOrEmpty(product.getpImagePath())) {
				i.setImagePath(product.getpImagePath());
				images.add(i);
			}

		}
		ImagesRequest request = new ImagesRequest(getBaseActivity()) {

			@Override
			public void onServiceReturned(ImagesResponse response) {
				if (response != null && response.getImages().size() > 0) {
					images.addAll(response.getImages());
					makeImageTransaction();
				} else if (images != null && images.size() > 0) {
					makeImageTransaction();
				} else {
					mViewProxy.findFrameLayout(R.id.gallery_adapter).setVisibility(View.GONE);
				}
			}
		};
		request.setIdProduct(product.getIdProduct());
		request.execute(false);
	}

	private void makeImageTransaction() {
		FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.gallery_adapter, ImageGallery.newInstance(images));
		transaction.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (images != null && images.size() > 0) {
			makeImageTransaction();
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.store_bottom, StoreBottomFragment.newInstance(store));
		transaction.commit();
		mViewProxy.findTextView(R.id.product_company).setText(store.getName());
		if (isOffer()) {
			mViewProxy.findTextView(R.id.product_name).setText(product.getoTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getoPrice()));
			mViewProxy.findTextView(R.id.product_description).setText(product.getoDescription());
		} else {
			mViewProxy.findTextView(R.id.product_name).setText(product.getpTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getpPrice()));
			mViewProxy.findTextView(R.id.product_description).setText(product.getpDescription());
		}
		

	}

	private boolean isOffer() {
		return product.getIdOffer() > 0;

	}

}
