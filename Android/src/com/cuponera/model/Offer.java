package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Offer extends BaseModel {

	@JsonProperty("Title")
	private String title;

	@JsonProperty("product")
	private Product product;

	@JsonProperty("Description")
	private String description;

	@JsonProperty("IdProduct")
	private Integer idProduct;

	@JsonProperty("ImagePath")
	private String imagePath;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
