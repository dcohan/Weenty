package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Images extends BaseModel {

	@JsonProperty("ImagePath")
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
