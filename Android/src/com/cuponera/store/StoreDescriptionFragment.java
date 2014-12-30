package com.cuponera.store;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Store;
import com.cuponera.product.ProductFragment;
import com.cuponera.service.images.ImagesRequest;
import com.cuponera.service.images.ImagesResponse;
import com.cuponera.utils.ImageGallery;
import com.cuponera.utils.ValidationUtils;

public class StoreDescriptionFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_store_description;
	}

	private static final String ARGS_STORE = "args_store";
	private static final String ARGS_ID_CATEGORY = "args_id_category";
	private Store store;

	public static StoreDescriptionFragment newInstance(int idCategory, Store s) {

		StoreDescriptionFragment fragment = new StoreDescriptionFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();

		b.putInt(ARGS_ID_CATEGORY, idCategory);
		b.putParcelable(ARGS_STORE, (Parcelable) s);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		request.setIdStore(store.getIdStore());
		request.execute(false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.store_bottom, StoreBottomFragment.newInstance(store));
		transaction.commit();
		
		mViewProxy.findTextView(R.id.product_company).setText(store.getName());
		if (store.hasOffers() || store.hasProducts()) {
			mViewProxy.findView(R.id.offer_circle).setVisibility(View.VISIBLE);
		}
		mViewProxy.findTextView(R.id.store_description).setText(store.getDescription());

		mViewProxy.findView(R.id.offer_circle).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, ProductFragment.newInstance(getArguments().getInt(ARGS_ID_CATEGORY), store));
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

		if (!ValidationUtils.isNullOrEmpty(store.getStoreHours())) {
			mViewProxy.findTextView(R.id.store_hours).setText(store.getStoreHours());
			mViewProxy.findTextView(R.id.store_hours).setVisibility(View.VISIBLE);
		}

	}
}