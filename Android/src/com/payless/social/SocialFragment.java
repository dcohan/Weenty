package com.payless.social;

import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cuponera.R;
import com.payless.BaseFragment;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.social.SocialListRequest;
import com.payless.service.social.SocialListResponse;
import com.payless.utils.EndlessScrollListener;

public class SocialFragment extends BaseFragment implements HeaderInterface {

	private SocialListAdapter adapter;
	private ListView mList;
	private EndlessScrollListener endlessScrollListener;

	@Override
	protected int getLayout() {
		return R.layout.fragment_social;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setList(mViewProxy.findListView(R.id.list));

		adapter = new SocialListAdapter(getBaseActivity());
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				getList().invalidateViews();
			}
		});

		getList().setAdapter(adapter);

		loadSocialEntries();

	}

	private void addMoreEntries(int page) {
		SocialListRequest request = new SocialListRequest(getBaseActivity()) {

			@Override
			public void loadFailed() {
			}

			@Override
			public void onServiceReturned(SocialListResponse result) {
				if (result.succes() && result.getItems() != null) {
					adapter.getItems().addAll(result.getItems());
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void hideLoading() {
			}

			@Override
			public void showLoading() {
			}
		};
		request.setPage(page);
		request.execute();
	}

	public void loadSocialEntries() {
		getList().setOnScrollListener(null);
		endlessScrollListener = new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				addMoreEntries(page);
			}
		};

		SocialListRequest request = new SocialListRequest(getBaseActivity()) {

			@Override
			public void loadFailed() {
			}

			@Override
			public void onServiceReturned(SocialListResponse result) {
				if (result.succes() && result.getItems() != null) {
					adapter.setItems(result.getItems());
					adapter.notifyDataSetChanged();
					getList().setOnScrollListener(endlessScrollListener);
				}
			}

			@Override
			public void hideLoading() {
				getBaseActivity().hideLoading();
			}

			@Override
			public void showLoading() {
				getBaseActivity().showLoading();
			}
		};

		request.execute();
	}

	@Override
	public String getTitle() {
		return getString(R.string.social);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return new HeaderImageInterface() {

			@Override
			public void onClickListener() {
				adapter.setItems(null);
				adapter.notifyDataSetChanged();
				loadSocialEntries();
			}

			@Override
			public Drawable getDrawable() {
				return getResources().getDrawable(R.drawable.refresh);
			}
		};
	}

	public ListView getList() {
		return mList;
	}

	public void setList(ListView mList) {
		this.mList = mList;
	}

}
