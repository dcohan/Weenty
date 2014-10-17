package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SocialEntry extends BaseModel {
	
	public enum Source {
		FB,
		TW
	}
	
	@JsonProperty("Source")
	private Source  source;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("ScreenName")
	private String screenName;
	
	@JsonProperty("TargetURL")
	private String targetURL;

	@JsonProperty("ImageURL")
	private String imageURL;

	@JsonProperty("Text")
	private String text;

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
}
