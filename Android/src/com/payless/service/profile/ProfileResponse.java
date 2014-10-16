package com.payless.service.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import com.payless.model.Profile;
import com.payless.service.BaseResponse;

public class ProfileResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("Profile")
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
