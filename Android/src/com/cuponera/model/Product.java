package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Product extends BaseModel {

	@JsonProperty("PTitle")
	private String pTitle;

	@JsonProperty("PImagePath")
	private String pImagePath;

	@JsonProperty("PDescription")
	private String pDescription;

	@JsonProperty("OTitle")
	private String oTitle;

	@JsonProperty("OImagePath")
	private String oImagePath;

	@JsonProperty("PPrice")
	private double pPrice;

	@JsonProperty("OPrice")
	private double oPrice;

	@JsonProperty("ODescription")
	private String oDescription;

	@JsonProperty("IdProduct")
	private int idProduct;

	@JsonProperty("IdOffer")
	private int idOffer;

	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public String getpImagePath() {
		return pImagePath;
	}

	public void setpImagePath(String pImagePath) {
		this.pImagePath = pImagePath;
	}

	public String getpDescription() {
		return pDescription;
	}

	public void setpDescription(String pDescription) {
		this.pDescription = pDescription;
	}

	public String getoTitle() {
		return oTitle;
	}

	public void setoTitle(String oTitle) {
		this.oTitle = oTitle;
	}

	public String getoImagePath() {
		return oImagePath;
	}

	public void setoImagePath(String oImagePath) {
		this.oImagePath = oImagePath;
	}

	public double getpPrice() {
		return pPrice;
	}

	public void setpPrice(double pPrice) {
		this.pPrice = pPrice;
	}

	public double getoPrice() {
		return oPrice;
	}

	public void setoPrice(double oPrice) {
		this.oPrice = oPrice;
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public int getIdOffer() {
		return idOffer;
	}

	public void setIdOffer(int idOffer) {
		this.idOffer = idOffer;
	}

	public String getoDescription() {
		return oDescription;
	}

	public void setoDescription(String oDescription) {
		this.oDescription = oDescription;
	}

}
