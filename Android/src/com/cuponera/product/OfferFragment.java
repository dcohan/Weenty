package com.cuponera.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.service.offer.OfferRequest;

public class OfferFragment extends BaseFragment {

	@Override
	protected int getLayout() {
		return R.layout.fragment_product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		OfferRequest request = new OfferRequest(getActivity()) {

			@Override
			public void onServiceReturned(com.cuponera.service.offer.OfferResponse result) {
				if (result != null) {
					ProductAdapter adapter = new ProductAdapter(getBaseActivity(), result.getOffers());
					mViewProxy.findListView(R.id.product_listview).setAdapter(adapter);
				}
			}
		};

		request.execute(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
