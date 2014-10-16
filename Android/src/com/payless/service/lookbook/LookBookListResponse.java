package com.payless.service.lookbook;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.payless.model.LookBookItem;
import com.payless.service.BaseResponse;

public class LookBookListResponse extends BaseResponse {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("Items")
	private List<LookBookItem> items;

	public List<LookBookItem> getItems() {
		return items;
	}

	public void setItems(List<LookBookItem> items) {
		this.items = items;
	}
	
}
