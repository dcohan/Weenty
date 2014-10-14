package com.payless.service.startapp;

import org.codehaus.jackson.annotate.JsonProperty;

import com.payless.model.Coupons;
import com.payless.model.HomeOffers;
import com.payless.model.LookBookFilters;
import com.payless.model.PreHome;
import com.payless.model.Profile;
import com.payless.service.BaseResponse;

public class StartAppResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("Digest")
	private String digest;

	@JsonProperty("PreHome")
	private PreHome preHome;

	@JsonProperty("HomeOffers")
	private HomeOffers homeOffers;

	@JsonProperty("Coupons")
	private Coupons coupons;

	@JsonProperty("Profile")
	private Profile profile;

	@JsonProperty("FilterLists")
	private LookBookFilters lookBookFilters;

	public PreHome getPreHome() {
		return preHome;
	}

	public void setPreHome(PreHome preHome) {
		this.preHome = preHome;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public HomeOffers getHomeOffers() {
		return homeOffers;
	}

	public void setHomeOffers(HomeOffers homeOffers) {
		this.homeOffers = homeOffers;
	}

	public Coupons getCoupons() {
		return coupons;
	}

	public void setCoupons(Coupons coupons) {
		this.coupons = coupons;
	}

	public LookBookFilters getLookBookFilters() {
		return lookBookFilters;
	}

	public void setLookBookFilters(LookBookFilters lookBookFilters) {
		this.lookBookFilters = lookBookFilters;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
