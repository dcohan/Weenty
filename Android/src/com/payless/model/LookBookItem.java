package com.payless.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class LookBookItem extends BaseModel {
	
	@JsonProperty("IdLookBookItem")
	private int idLookBookItem;

	@JsonProperty("ImageURL")
	private String imageURL;

	@JsonProperty("TargetURL")
	private String targetURL;

	@JsonProperty("Description")
	private String description;

	@JsonProperty("Name")
	private String name;
	
	public int getIdLookBookItem() {
		return idLookBookItem;
	}

	public void setIdLookBookItem(int idLookBookItem) {
		this.idLookBookItem = idLookBookItem;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
