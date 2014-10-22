package com.cuponera.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomeOffers extends BaseModel {
	
	@JsonProperty("HomeOffersLastUpdate")
	private Date homeOffersLastUpdate;
	
	@JsonProperty("HomeOffers")
	private List<HomeOffer> offers;

	public Date getHomeOffersLastUpdate() {
		return homeOffersLastUpdate;
	}

	public void setHomeOffersLastUpdate(Date homeOffersLastUpdate) {
		this.homeOffersLastUpdate = homeOffersLastUpdate;
	}

	public List<HomeOffer> getOffers() {
		return offers;
	}

	public void setOffers(List<HomeOffer> offers) {
		this.offers = offers;
	}
	
	
}
