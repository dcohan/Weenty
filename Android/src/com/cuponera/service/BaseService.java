package com.cuponera.service;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6134787886852299061L;

	/**
	 * 
	 */

	private boolean success = true;

	@JsonProperty("MorePages")
	private boolean morePages = false;

	@JsonProperty("Result")
	private String result;

	@JsonProperty("ErrorCode")
	private int errorCode;

	@JsonProperty("Errors")
	private List<ErrorModelResponse> errors;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isMorePages() {
		return morePages;
	}

	public void setMorePages(boolean morePages) {
		this.morePages = morePages;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errors
	 */
	public List<ErrorModelResponse> getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(List<ErrorModelResponse> errors) {
		this.errors = errors;
	}

}