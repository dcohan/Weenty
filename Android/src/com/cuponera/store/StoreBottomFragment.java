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
import com.cuponera.analytics.AnalyticsHelpers;
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

		mViewProxy.findTextView(R.id.store_address).setText(store.getAddress());
		
		mViewProxy.findTextView(R.id.store_mail).setText(store.getEmail());

		OnClickListener mailClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent result = new Intent(android.content.Intent.ACTION_SENDTO);
				result.setType("plain/text");
				result.setData(Uri.parse("mailto:" + store.getEmail()));
				result.putExtra(android.content.Intent.EXTRA_EMAIL, store.getEmail());
				result.putExtra(android.content.Intent.EXTRA_SUBJECT, store.getName());
				startActivity(result);

			}
		};
		OnClickListener phoneClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + store.getContactNumber()));
				startActivity(callIntent);

			}
		};

		if (!ValidationUtils.isNullOrEmpty(store.getContactNumber())) {
			mViewProxy.findTextView(R.id.store_phone).setText(store.getContactNumber());
			mViewProxy.findTextView(R.id.store_phone).setVisibility(View.VISIBLE);
			mViewProxy.findTextView(R.id.store_phone).setOnClickListener(phoneClick);
			mViewProxy.findImageView(R.id.product_phone_image).setVisibility(View.VISIBLE);
			mViewProxy.findImageView(R.id.product_phone_image).setOnClickListener(phoneClick);
		}

		if (!ValidationUtils.isNullOrEmpty(store.getEmail())) {
			mViewProxy.findImageView(R.id.product_mail).setVisibility(View.VISIBLE);
			mViewProxy.findTextView(R.id.store_mail).setVisibility(View.VISIBLE);
			mViewProxy.findImageView(R.id.product_mail).setOnClickListener(mailClick);
			mViewProxy.findTextView(R.id.store_mail).setOnClickListener(mailClick);
		}

		mViewProxy.findImageView(R.id.product_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsHelpers.getInstance().logScreenWithEvent(AnalyticsHelpers.MAP, store.getName());
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
					if (store.getFacebookUrl().contains("http")) {
						getBaseActivity().openURL(store.getFacebookUrl());
					} else {
						getBaseActivity().openURL("http://" + store.getFacebookUrl());
					}
				}
			});

		}

		if (!ValidationUtils.isNullOrEmpty(store.getWebPage())) {
			mViewProxy.findTextView(R.id.store_web_page).setVisibility(View.VISIBLE);
			mViewProxy.findTextView(R.id.store_web_page).setText(store.getWebPage());
			mViewProxy.findTextView(R.id.store_web_page).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (store.getWebPage().contains("http")) {
						getBaseActivity().openURL(store.getWebPage());
					} else {
						getBaseActivity().openURL("http://" + store.getWebPage());
					}

				}
			});

		}
	}
}
