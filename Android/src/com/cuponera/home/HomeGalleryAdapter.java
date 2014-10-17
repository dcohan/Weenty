package com.cuponera.home;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cuponera.model.HomeOffer;
import com.cuponera.model.HomeOffers;

public class HomeGalleryAdapter extends FragmentPagerAdapter{
	
	public HomeGalleryAdapter(FragmentManager fm) {
		super(fm);
	}

	private List<HomeOffer> offers = new ArrayList<HomeOffer>();
	
	public void setData(HomeOffers homeOffers) {
		if(homeOffers != null
				&& homeOffers.getOffers() != null){
			offers = homeOffers.getOffers();
		}
	}
	
	@Override
	public Fragment getItem(int arg0) {

		HomeOffer offer = null;
		
		if(hasInfiniteScroll()
				&& arg0 == 0) {
			offer = offers.get(offers.size() - 1); //Last one
		} else if(hasInfiniteScroll()
				&& arg0 == getCount() - 1) {
			offer = offers.get(0); //First one
		} else {
			offer = offers.get(arg0 - 1);
		}
		
		Fragment fragment = HomeImageFragment.newInstance(offer);
		return fragment;
	}

	@Override
	public int getCount() {
		int size = offers.size();
		if(size > 1){
			size+=2;
		}
		return size;
	}
	
	public boolean hasInfiniteScroll() {
		return getCount() > 1;
	}

}
