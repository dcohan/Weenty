package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class State extends BaseModel {

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Link")
	private String link;

	@JsonProperty("Longitude")
	private double longitude;

	@JsonProperty("Latitude")
	private double latitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
