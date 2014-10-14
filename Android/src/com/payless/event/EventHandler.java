package com.payless.event;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {

	private List<HandlerReference> mHandlerRef = new ArrayList<HandlerReference>();

	public EventHandler() {

	}

	public void addListener(EventListener<? extends BaseEvent> handler, Class<? extends BaseEvent> type) {
		synchronized (mHandlerRef) {
			mHandlerRef.add(new HandlerReference(handler, type));
		}
	}

	public <E extends BaseEvent> void removeListener(EventListener<E> listener) {
		synchronized (mHandlerRef) {
			for (HandlerReference ref : mHandlerRef) {
				if (ref.handler == listener) {
					mHandlerRef.remove(ref);
					break;
				}
			}
		}
	}

	public void dispatchEvent(BaseEvent event) {
		synchronized (mHandlerRef) {
			for (HandlerReference reference : mHandlerRef) {
				if (reference.getType() == event.getClass()) {
					reference.getHandler().dipatchEvent(event);
				}
			}
		}
	}

	public class HandlerReference {

		private EventListener<? extends BaseEvent> handler;
		private Class<? extends BaseEvent> type;

		public HandlerReference(EventListener<? extends BaseEvent> handler, Class<? extends BaseEvent> type) {
			this.handler = handler;
			this.type = type;
		}

		public EventListener<? extends BaseEvent> getHandler() {
			return handler;
		}

		public void setHandler(EventListener<? extends BaseEvent> handler) {
			this.handler = handler;
		}

		public Class<? extends BaseEvent> getType() {
			return type;
		}

		public void setType(Class<? extends BaseEvent> type) {
			this.type = type;
		}

	}
}
