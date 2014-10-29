package com.cuponera.service.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.Profile;
import com.cuponera.service.BaseResponse;

public class ProfileResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("Data")
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
