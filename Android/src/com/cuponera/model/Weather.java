package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Weather extends BaseModel {

	@JsonProperty("description")
	private String description;

	@JsonProperty("icon")
	private String icon;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


}
