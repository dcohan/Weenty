package com.cuponera.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuponera.R;
import com.cuponera.model.State;

public class CustomPageStateAdapter extends PagerAdapter {

	private List<State> items;
	private LayoutInflater inflater;
	private Context context;

	public CustomPageStateAdapter(Context context, ArrayList<State> items) {
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
		this.context = context;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View itemView;
		itemView = inflater.inflate(R.layout.state_adapter, container, false);

		TextView topTextItem = (TextView) itemView.findViewById(R.id.topText);
		if (position + 1 == getCount()) {
			topTextItem.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_action_arrow_left), null, null, null);
		} else if (position == 0) {
			topTextItem.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_action_arrow_right), null);
		}
		State s = items.get(position);

		topTextItem.setText(s.getName());

		((ViewPager) container).addView(itemView);

		return itemView;
	}

}
