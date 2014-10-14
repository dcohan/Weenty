package com.payless.event;

public class BaseEvent {

	private Class<?> type = this.getClass();

	public BaseEvent(Class<?> type) {
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

}