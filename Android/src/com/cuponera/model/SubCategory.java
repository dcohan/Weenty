package com.cuponera.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class SubCategory extends BaseModel {

	@JsonProperty("IdSubCategory")
	private int id;

	@JsonProperty("Name")
	private String name;

	private ArrayList<Integer> idStores;

	private boolean isSelected;

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

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ArrayList<Integer> getIdStores() {
		return idStores;
	}

	public void setIdStores(ArrayList<Integer> idStores) {
		this.idStores = idStores;
	}

}
