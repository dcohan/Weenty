package com.cuponera.store;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.map.GoogleMapFragment;
import com.cuponera.model.Store;
import com.cuponera.utils.ValidationUtils;

public class StoreBottomFragment extends BaseFragment {

	private static final String ARGS_STORE = "args_store";
	private Store store;

	@Override
	protected int getLayout() {
		return R.layout.fragment_store_bottom;
	}

	public static StoreBottomFragment newInstance(Store s) {

		StoreBottomFragment fragment = new StoreBottomFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();

		b.putParcelable(ARGS_STORE, (Parcelable) s);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		store = getArguments().getParcelable(ARGS_STORE);
		mViewProxy.findTextView(R.id.store_phone).setText(store.getContactNumber());
		mViewProxy.findTextView(R.id.store_address).setText(store.getAddress());
		mViewProxy.findTextView(R.id.store_mail).setText(store.getEmail());

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
					Intent result = new Intent(android.content.Intent.ACTION_SENDTO);
					result.setType("plain/text");
					result.setData(Uri.parse("mailto:" + store.getEmail()));
					result.putExtra(android.content.Intent.EXTRA_EMAIL, store.getEmail());
					result.putExtra(android.content.Intent.EXTRA_SUBJECT, store.getName());
					startActivity(result);
				}
			});
		}

		mViewProxy.findImageView(R.id.product_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Store> storeArray = new ArrayList<Store>();
				storeArray.add(store);
				if (storeArray.get(0) != null && storeArray.get(0).getLatitude() != 0) {
					getBaseActivity().pushFragment(GoogleMapFragment.newInstance(storeArray), true);
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
