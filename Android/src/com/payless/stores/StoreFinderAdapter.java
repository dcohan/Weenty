package com.payless.stores;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.cuponera.R;
import com.payless.BaseActivity;
import com.payless.helpers.AnalyticsHelper;
import com.payless.map.GoogleMapFragment;
import com.payless.model.Item;
import com.payless.utils.BaseListAdapter;
import com.payless.utils.LocationServices;
import com.payless.utils.ViewProxy;

public class StoreFinderAdapter extends BaseListAdapter {
	private ArrayList<Item> items;
	private Activity activity;
	private Item i;

	public StoreFinderAdapter(Activity activity) {
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getIdStore();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewProxy mViewProxy = new ViewProxy(activity, R.layout.adapter_store_finder);
		final LinearLayout datesList = mViewProxy.findLinearLayout(R.id.dates_listview_containter);
		i = items.get(position);

		mViewProxy.findTextView(R.id.payless_store_name).setText(i.getName());
		mViewProxy.findTextView(R.id.payless_address).setText(i.getAddress());
		mViewProxy.findTextView(R.id.payless_distance).setText(
				String.format(activity.getResources().getString(R.string.store_distance), i.getDistance()));
		if (i.getDistance() != -1.0) {
			SpannableStringBuilder sb = new SpannableStringBuilder(mViewProxy.findTextView(R.id.payless_distance).getText() + " miles");
			ForegroundColorSpan fcs = new ForegroundColorSpan(activity.getResources().getColor(R.color.orange_dark));
			sb.setSpan(fcs, 0, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

			mViewProxy.findTextView(R.id.payless_distance).setText(sb);
			mViewProxy.findTextView(R.id.payless_distance).setVisibility(View.VISIBLE);
		}
		mViewProxy.findTextView(R.id.payless_phone).setText(i.getContactNumber());
		mViewProxy.findTextView(R.id.payless_store_name_with_hours).setText(
				i.getName() + " " + activity.getResources().getString(R.string.store_hours));

		StoresDaysAdapter storeDaysAdapter = new StoresDaysAdapter(activity, i.getStoreHours());
		mViewProxy.findListView(R.id.dates_listview).setAdapter(storeDaysAdapter);

		mViewProxy.findLinearLayout(R.id.adapter_store_main).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RotateAnimation rotate;
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(500);
				if (datesList.getVisibility() == View.VISIBLE) {
					rotate = new RotateAnimation(90, (float) 0, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
					datesList.setAnimation(animation);
					datesList.setVisibility(View.GONE);
				} else {
					AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.STORE_FINDER_RESULTS, AnalyticsHelper.LIST,
							String.valueOf(i.getIdStore()));
					rotate = new RotateAnimation(0, (float) 90, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
					datesList.setAnimation(animation);
					datesList.setVisibility(View.VISIBLE);
				}
				rotate.setDuration(500);
				rotate.setFillAfter(true);
				mViewProxy.findImageView(R.id.store_details).startAnimation(rotate);
			}
		});

		mViewProxy.findImageView(R.id.store_call).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategoryAndLabelAndValue(AnalyticsHelper.STORE, AnalyticsHelper.LIST, AnalyticsHelper.TELEPHONE,
						i.getIdStore());
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + mViewProxy.findTextView(R.id.payless_phone).getText()));
				activity.startActivity(intent);
			}
		});

		mViewProxy.findImageView(R.id.store_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i.getLocation() != null) {
					AnalyticsHelper.logEventWithCategoryAndLabelAndValue(AnalyticsHelper.STORE, AnalyticsHelper.LIST, AnalyticsHelper.STORE_MAP,
							i.getIdStore());
					((BaseActivity) activity).pushFragment(GoogleMapFragment.newInstance(i), true);
				}
			}
		});
		final Location location = LocationServices.getInstance(activity).getLocation();
		mViewProxy.findImageView(R.id.store_location).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (location != null && i.getLocation() != null) {
					AnalyticsHelper.logEventWithCategoryAndLabelAndValue(AnalyticsHelper.STORE, AnalyticsHelper.LIST,
							AnalyticsHelper.STORE_INDICATIONS, i.getIdStore());
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
							+ location.getLatitude() + "," + location.getLongitude() + "&daddr=" + i.getLocation().getLatitude() + ","
							+ i.getLocation().getLongitude()));
					activity.startActivity(intent);
				}
			}
		});
		return mViewProxy.getView();
	}

	public ArrayList<Item> getResults() {
		return items;
	}

	public void setResults(ArrayList<Item> results) {
		this.items = results;
	}
}
