package com.cuponera.event;

import java.util.ArrayList;
import java.util.List;

public class ErrorEvent extends BaseEvent {

	private List<Integer> errorMessages;
	private boolean back = false;
	private String exceptionMessage;
	
	public ErrorEvent(int errorMessage) {
		super(ErrorEvent.class);
		this.errorMessages = new ArrayList<Integer>();
		this.errorMessages.add(errorMessage);
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public ErrorEvent(int errorMessage, String exceptionMessage) {
		this(errorMessage);
		this.exceptionMessage = exceptionMessage;
	}
	
	public ErrorEvent(int errorMessage, boolean goBack) {
		this(errorMessage);
		this.back = goBack;
	}
	
	public List<Integer> getErrorMessages() {
		return errorMessages;
	}
	
	public boolean isBack() {
		return back;
	}


	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}