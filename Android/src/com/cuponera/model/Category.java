package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Category extends BaseModel {

	@JsonProperty("Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
