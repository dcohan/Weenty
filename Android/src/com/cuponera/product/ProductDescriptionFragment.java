package com.cuponera.product;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.map.GoogleMapFragment;
import com.cuponera.model.Product;
import com.cuponera.model.Store;
import com.cuponera.utils.ValidationUtils;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoad;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadUrl;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScroller;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScrollerAdapter;

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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		product = getArguments().getParcelable(ARGS_PRODUCT);
		store = getArguments().getParcelable(ARGS_STORE);

		mViewProxy.findTextView(R.id.product_company).setText(store.getName());
		mViewProxy.findTextView(R.id.product_description).setText(product.getpDescription());
		if (product.getIdOffer() > 0) {
			mViewProxy.findTextView(R.id.product_name).setText(product.getoTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getoPrice()));
		} else {
			mViewProxy.findTextView(R.id.product_name).setText(product.getpTitle());
			mViewProxy.findTextView(R.id.product_price).setText("$" + String.valueOf(product.getpPrice()));
		}
		if (!ValidationUtils.isNullOrEmpty(store.getStoreHours())) {
			mViewProxy.findTextView(R.id.store_hours).setText(store.getStoreHours());
			mViewProxy.findTextView(R.id.store_hours).setVisibility(View.VISIBLE);
		}

		mViewProxy.findTextView(R.id.product_phone).setText(getResources().getString(R.string.phone) + store.getContactNumber());

		ArrayList<ImageToLoad> images = new ArrayList<ImageToLoad>();
		for (int i = 0; i < 20; i++) {
			images.add(new ImageToLoadUrl("https://www.google.com.ar/images/google_favicon_128.png"));
		}

		HorizontalImageScroller scroller = (HorizontalImageScroller) mViewProxy.findView(R.id.my_horizontal_image_scroller);
		scroller.setAdapter(new HorizontalImageScrollerAdapter(getActivity(), images));
		scroller.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

		if (!ValidationUtils.isNullOrEmpty(store.getContactNumber())) {
			mViewProxy.findImageView(R.id.product_phone_image).setVisibility(View.VISIBLE);
			mViewProxy.findImageView(R.id.product_phone_image).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + store.getContactNumber()));
					startActivity(callIntent);
				}
			});
		}

		if (!ValidationUtils.isNullOrEmpty(store.getEmail())) {
			mViewProxy.findImageView(R.id.product_mail).setVisibility(View.VISIBLE);
			mViewProxy.findImageView(R.id.product_mail).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent result = new Intent(android.content.Intent.ACTION_SEND);
					result.setType("plain/text");
					result.putExtra(android.content.Intent.EXTRA_EMAIL, store.getEmail());
					result.putExtra(android.content.Intent.EXTRA_SUBJECT, product.getpTitle());
					startActivity(result);
				}
			});
		}

		mViewProxy.findImageView(R.id.product_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (store.getLatitude() != 0) {
					getBaseActivity().pushFragment(GoogleMapFragment.newInstance(store), true);
				}
			}
		});

		if (!ValidationUtils.isNullOrEmpty(store.getFacebookUrl())) {
			mViewProxy.findImageView(R.id.product_fb).setVisibility(View.VISIBLE);
			mViewProxy.findImageView(R.id.product_fb).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + store.getFacebookUrl())));
				}
			});

		}
	}

}
