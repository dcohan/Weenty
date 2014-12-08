package com.cuponera.service.prehome;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.PreHome;
import com.cuponera.service.BaseResponse;

public class PrehomeResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("value")
	private ArrayList<PreHome> preHomeImages;

	public ArrayList<PreHome> getPreHomeImages() {
		return preHomeImages;
	}

	public void setPreHomeImages(ArrayList<PreHome> preHomeImages) {
		this.preHomeImages = preHomeImages;
	}

}
