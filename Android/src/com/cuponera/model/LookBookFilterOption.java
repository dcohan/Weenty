package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class LookBookFilterOption extends BaseModel {
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Value")
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
