package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class PreHome extends BaseModel {
	
	@JsonProperty("ImagePath")
	private String prehomeImage;

	public String getPrehomeImage() {
		return prehomeImage;
	}

	public void setPrehomeImage(String prehomeImage) {
		this.prehomeImage = prehomeImage;
	}


}
