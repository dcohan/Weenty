package com.cuponera.service.prehome;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.service.BaseResponse;

public class PrehomeResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("ImagePath")
	private String prehomeImage;

	public String getPrehomeImage() {
		return prehomeImage;
	}

	public void setPrehomeImage(String prehomeImage) {
		this.prehomeImage = prehomeImage;
	}

}
