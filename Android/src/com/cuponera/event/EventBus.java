package com.cuponera.event;


public class EventBus extends EventHandler {

	private static EventBus mInstance;
	
	private EventBus(){
		
	}
	
	public static EventBus getInstance(){
		
		if(mInstance == null){
			mInstance = new EventBus();
		}
		
		return mInstance;
	}
	
}