package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Product extends BaseModel {

	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("company")
	private Company company;
	
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
