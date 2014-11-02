package com.cuponera.service.category;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Category;
import com.cuponera.service.BaseResponse;

public class CategoryResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Category> category;

	public ArrayList<Category> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<Category> category) {
		this.category = category;
	}


}
