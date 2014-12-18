package com.cuponera.service.images;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Images;
import com.cuponera.service.BaseResponse;

public class ImagesResponse extends BaseResponse {

	private static final long serialVersionUID = -4029237294641698732L;

	@JsonProperty("value")
	private ArrayList<Images> images;

	public ArrayList<Images> getImages() {
		return images;
	}

	public void setImages(ArrayList<Images> images) {
		this.images = images;
	}

}
