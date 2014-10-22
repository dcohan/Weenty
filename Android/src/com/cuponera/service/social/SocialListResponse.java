package com.cuponera.service.social;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.SocialEntry;
import com.cuponera.service.BaseResponse;

public class SocialListResponse extends BaseResponse {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("Items")
	private List<SocialEntry> items;

	public List<SocialEntry> getItems() {
		return items;
	}

	public void setItems(List<SocialEntry> items) {
		this.items = items;
	}
	
}
