package com.cuponera.service.offer;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Offer;
import com.cuponera.service.BaseResponse;

public class OfferResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Offer> offer;

	public ArrayList<Offer> getOffers() {
		return offer;
	}

	public void setOffers(ArrayList<Offer> offer) {
		this.offer = offer;
	}


}
