package com.payless.service.stores;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.payless.model.Item;
import com.payless.service.BaseResponse;

public class StoresResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("Items")
	private ArrayList<Item> items;

	@JsonProperty("Total")
	private Integer total;

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
