package com.cuponera.service.store;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.IProduct;
import com.cuponera.model.Product;
import com.cuponera.service.BaseResponse;

public class StoreResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Product> product;

	public ArrayList<? extends IProduct> getProducts() {
		return (ArrayList<? extends IProduct>)product;
	}

	public void setProducts(ArrayList<Product> product) {
		this.product = product;
	}


}
