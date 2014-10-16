package com.payless.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.payless.BaseFragment;
import com.payless.R;
import com.payless.helpers.AnalyticsHelper;
import com.payless.model.Item;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;

public class GoogleMapFragment extends BaseFragment implements HeaderInterface {

	private Item item;
	private static final String ARGS_ITEM = "args_item";
	private MapView mapView;
	private GoogleMap map;

	public static GoogleMapFragment newInstance(Item item) {

		GoogleMapFragment fragment = new GoogleMapFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();

		b.putParcelable(ARGS_ITEM, item);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnalyticsHelper.logEvent(AnalyticsHelper.MAP_SCREEN);
		MapsInitializer.initialize(getActivity());
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.activity_map, container, false);
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		map = mapView.getMap();

		item = getArguments().getParcelable(ARGS_ITEM);

		LatLng mapCenter = new LatLng(item.getLocation().getLatitude(), item.getLocation().getLongitude());

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

		CameraPosition cameraPosition = CameraPosition.builder().target(mapCenter).zoom(13).bearing(90).build();

		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

		map.addMarker(new MarkerOptions().position(mapCenter).title(item.getName().toUpperCase())
				.snippet(item.getAddress() + ", " + item.getCity() + ", " + item.getState().toUpperCase())
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)));
		return v;
	}

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public String getTitle() {
		return getString(R.string.map);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_map;
	}
}