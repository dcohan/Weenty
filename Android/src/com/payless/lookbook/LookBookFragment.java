package com.payless.lookbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.cuponera.R;
import com.payless.BaseFragment;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.helpers.AnalyticsHelper;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.lookbook.LookBookListRequest;
import com.payless.service.lookbook.LookBookListResponse;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;

public class LookBookFragment extends BaseFragment implements HeaderInterface {

	private ListView lookBookFilters;
	private Map<String, String> appliedFilters = new HashMap<String, String>();

	@Override
	protected int getLayout() {
		return R.layout.fragment_lookbook;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		lookBookFilters = mViewProxy.findListView(R.id.lookBookFilters);
		LookBookFiltersAdapter adapter = new LookBookFiltersAdapter(getBaseActivity()) {

			@Override
			public void onItemSelected(String field, String value) {
				if (!Utils.isBlankOrZero(value)) {
					appliedFilters.put(field, value);
				} else {
					appliedFilters.remove(field);
				}
			}

		};

		lookBookFilters.setAdapter(adapter);

		mViewProxy.findButton(R.id.search_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategory(AnalyticsHelper.LOOKBOOK, AnalyticsHelper.SEARCH);

				final ArrayList<String> filters = new ArrayList<String>();
				for (Map.Entry<String, String> entry : appliedFilters.entrySet()) {
					filters.add(entry.getValue());
				}

				LookBookListRequest request = null;
				request = new LookBookListRequest(getBaseActivity()) {

					@Override
					public void loadFailed() {
					}

					@Override
					public void onServiceReturned(LookBookListResponse result) {
						if (result.succes()) {
							if (result.getItems().size() > 0) {
								getBaseActivity().pushFragment(LookBookResultsFragment.newInstance(result, filters), true);
							} else {
								EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.NO_RESULTS_FOUND));
							}
						}
					}

					@Override
					public void showLoading() {
						getBaseActivity().showLoading();
					}

					@Override
					public void hideLoading() {
						getBaseActivity().hideLoading();
					}
				};
				request.setFilters(filters);
				request.execute();
			}
		});
	}

	@Override
	public String getTitle() {
		return getString(R.string.look_book_title);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

}
