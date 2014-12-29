package com.cuponera.service.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.service.BaseResponse;

public class ProfileResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("IdProfile")
	private String profileID;

	@JsonProperty("Geolocation")
	private boolean geolocation;


	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public boolean isGeolocation() {
		return geolocation;
	}

	public void setGeolocation(boolean geolocation) {
		this.geolocation = geolocation;
	}
	

}
