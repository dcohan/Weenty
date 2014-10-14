package com.payless.service;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseResponse implements Serializable, Parcelable {

	private static final long serialVersionUID = 6895161269309561004L;

	@JsonProperty("Result")
	private boolean result;

	@JsonProperty("Errors")
	private List<ErrorModelResponse> errors;
	
	@JsonIgnore
	private JSONObject data;
	
	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public List<ErrorModelResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorModelResponse> errors) {
		this.errors = errors;
	}
	
	public boolean succes() {
		return getResult();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}