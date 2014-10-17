package com.cuponera.social;

import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cuponera.BaseActivity;
import com.cuponera.R;
import com.cuponera.helpers.AnalyticsHelper;
import com.cuponera.model.SocialEntry;
import com.cuponera.utils.BaseListAdapter;
import com.cuponera.utils.Utils;
import com.cuponera.utils.ViewProxy;

public class SocialListAdapter extends BaseListAdapter {

	private List<SocialEntry> items;
	private Context context;

	public SocialListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return (getItems() != null) ? getItems().size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewProxy viewProxy = new ViewProxy(context, R.layout.social_row);

		final SocialEntry entry = getItems().get(position);

		Utils.loadImageFromUrl(viewProxy.findImageView(R.id.image), entry.getImageURL());

		SpannableStringBuilder sb = new SpannableStringBuilder();
		// Name
		sb.append(entry.getName());

		// ScreenName
		if (!Utils.isBlankOrZero(entry.getScreenName())) {
			sb.append(" @" + entry.getScreenName());
		}

		// Description
		sb.append("\n" + entry.getText());

		// Link
		sb.append(" " + entry.getTargetURL());

		ForegroundColorSpan colorTitle = new ForegroundColorSpan(context.getResources().getColor(R.color.orange_dark));
		sb.setSpan(colorTitle, 0, entry.getName().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		ForegroundColorSpan colorLink = new ForegroundColorSpan(context.getResources().getColor(R.color.blue_twitter));
		sb.setSpan(colorLink, sb.length() - entry.getTargetURL().length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		sb.setSpan(new UnderlineSpan(), sb.length() - entry.getTargetURL().length(), sb.length(), 0);

		viewProxy.findTextView(R.id.text).setText(sb);

		viewProxy.getView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.SOCIAL, AnalyticsHelper.FEED, entry.getSource().name());
				((BaseActivity) context).openURL(entry.getTargetURL(), context.getString(R.string.social));
			}
		});

		return viewProxy.getView();
	}

	public List<SocialEntry> getItems() {
		return items;
	}

	public void setItems(List<SocialEntry> items) {
		this.items = items;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}