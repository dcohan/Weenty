package com.cuponera.map;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuponera.BaseFragment;
import com.cuponera.R;
import com.cuponera.model.Store;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends BaseFragment {

	private ArrayList<Store> item;
	private static final String ARGS_ITEM = "args_item";
	private MapView mapView;
	private GoogleMap map;

	public static GoogleMapFragment newInstance(ArrayList<Store> item) {

		GoogleMapFragment fragment = new GoogleMapFragment();
		Bundle b = fragment.getArguments();
		if (b == null)
			b = new Bundle();

		b.putParcelableArrayList(ARGS_ITEM, item);

		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

		item = getArguments().getParcelableArrayList(ARGS_ITEM);
		int firstElementList = 0;
		for (Store store : item) {
			if (store.getLatitude() != 0) {
				firstElementList++;
				LatLng mapCenter = new LatLng(store.getLatitude(), store.getLongitude());

				if (firstElementList == 1) {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));
					CameraPosition cameraPosition = CameraPosition.builder().target(mapCenter).zoom(13).bearing(90).build();
					map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
				}

				map.addMarker(new MarkerOptions().position(mapCenter).title(store.getName().toUpperCase()).snippet(store.getAddress())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location)));

			}
		}

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
	protected int getLayout() {
		return R.layout.activity_map;
	}
}