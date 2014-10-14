package com.payless.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Coupon extends BaseModel {

	@JsonProperty("IdCoupon")
	private int idCoupon;

	@JsonProperty("TargetURL")
	private String targetURL;

	@JsonProperty("StartDatetime")
	private Date startDatetime;

	@JsonProperty("ExpirationDatetime")
	private String expirationDatetime;

	@JsonProperty("ImageURL")
	private String imageURL;

	@JsonProperty("Title")
	private String title;

	@JsonProperty("PromoCode")
	private String promoCode;

	public int getIdCoupon() {
		return idCoupon;
	}

	public void setIdCoupon(int idCoupon) {
		this.idCoupon = idCoupon;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public Date getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public String getExpirationDatetime() {
		return expirationDatetime;
	}

	public void setExpirationDatetime(String expirationDatetime) {
		this.expirationDatetime = expirationDatetime;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

}
