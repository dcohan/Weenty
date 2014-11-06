package com.cuponera.service.product;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Product;
import com.cuponera.service.BaseResponse;

public class ProductResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Product> product;

	public ArrayList<Product> getProducts() {
		return product;
	}

	public void setProducts(ArrayList<Product> product) {
		this.product = product;
	}


}
