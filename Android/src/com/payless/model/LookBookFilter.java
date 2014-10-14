package com.payless.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class LookBookFilter extends BaseModel {
	
	@JsonProperty("Field")
	private String field;

	@JsonProperty("FieldString")
	private String fieldString;

	@JsonProperty("Options")
	private List<LookBookFilterOption> options;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldString() {
		return fieldString;
	}

	public void setFieldString(String fieldString) {
		this.fieldString = fieldString;
	}

	public List<LookBookFilterOption> getOptions() {
		return options;
	}

	public void setOptions(List<LookBookFilterOption> options) {
		this.options = options;
	}
	
}
