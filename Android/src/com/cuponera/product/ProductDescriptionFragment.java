package com.cuponera.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Product;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoad;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadDrawableResource;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadUrl;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScroller;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScrollerAdapter;

public class ProductDescriptionFragment extends BaseFragment {

	private static final String ARGS_PRODUCT = "args_id_category";
	private Product product;

	@Override
	protected int getLayout() {
		return R.layout.fragment_product_description;
	}

	public static ProductDescriptionFragment newInstance(Product p) {

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
		product = getArguments().getParcelable(ARGS_PRODUCT);
		// mViewProxy.findTextView(R.id.product_company).setText(iProduct.getCompany().getName());
		mViewProxy.findTextView(R.id.product_name).setText(product.getpTitle());
		mViewProxy.findTextView(R.id.product_description).setText(product.getpDescription());
		ArrayList<ImageToLoad> images = new ArrayList<ImageToLoad>();
		for (int i = 0; i < 20; i++) {
			images.add(new ImageToLoadUrl("https://www.google.com.ar/images/google_favicon_128.png"));
			images.add(new ImageToLoadDrawableResource(R.drawable.oferta3test));
		}

		HorizontalImageScroller scroller = (HorizontalImageScroller) mViewProxy.findView(R.id.my_horizontal_image_scroller);
		scroller.setAdapter(new HorizontalImageScrollerAdapter(getActivity(), images));
		scroller.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

		// mViewProxy.findImageView(R.id.product_map).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (iProduct.getLocation() != null) {
		// getBaseActivity().pushFragment(GoogleMapFragment.newInstance(iProduct),
		// true);
		// }
		// }
		// });

	}

}
