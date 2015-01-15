package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SubCategory extends BaseModel {

	@JsonProperty("IdSubCategory")
	private int id;

	@JsonProperty("Name")
	private String name;
	
	private int idStore;

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

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	
}
