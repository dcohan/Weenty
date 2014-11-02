package com.cuponera.service.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.service.BaseResponse;

public class ProfileResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("IdProfile")
	private String profileID;

	@JsonProperty("Geolocation")
	private int geolocation;


	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public int isGeolocation() {
		return geolocation;
	}

	public void setGeolocation(int geolocation) {
		this.geolocation = geolocation;
	}
	

}
