package com.cuponera.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomeOffer extends BaseModel {
	
	@JsonProperty("IdHomeOffer")
	private int idHomeOffer;

	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("TargetURL")
	private String targetURL;

	@JsonProperty("StartDatetime")
	private Date startDatetime;

	@JsonProperty("ExpirationDatetime")
	private Date expirationDatetime;

	@JsonProperty("ImageURL")
	private String imageURL;

	public int getIdHomeOffer() {
		return idHomeOffer;
	}

	public void setIdHomeOffer(int idHomeOffer) {
		this.idHomeOffer = idHomeOffer;
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

	public Date getExpirationDatetime() {
		return expirationDatetime;
	}

	public void setExpirationDatetime(Date expirationDatetime) {
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
	
	
	
}
