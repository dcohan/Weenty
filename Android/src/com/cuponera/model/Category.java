package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Category extends BaseModel {

	@JsonProperty("IdCategory")
	private int id;

	@JsonProperty("Name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
