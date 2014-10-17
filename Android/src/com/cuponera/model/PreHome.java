package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class PreHome extends BaseModel {
	
	@JsonProperty("IdPreHomeImage")
	private int idPreHomeImage;
	
	@JsonProperty("ImageURL")
	private String imageURL;

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
