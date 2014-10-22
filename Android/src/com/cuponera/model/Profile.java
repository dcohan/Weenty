package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Profile extends BaseModel {

	@JsonProperty("IdProfile")
	private String profileID;

	private String city;

	@JsonProperty("Geolocation")
	private boolean geolocation;

	@JsonProperty("IsFirstRun")
	private boolean isFirstRun;

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

	public boolean isFirstRun() {
		return isFirstRun;
	}

	public void setFirstRun(boolean isFirstRun) {
		this.isFirstRun = isFirstRun;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
