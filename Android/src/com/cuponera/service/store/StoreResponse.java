package com.cuponera.service.store;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Store;
import com.cuponera.service.BaseResponse;

public class StoreResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Store> store;

	public ArrayList<Store> getStore() {
		return store;
	}

	public void setStore(ArrayList<Store> store) {
		this.store = store;
	}
	

}
