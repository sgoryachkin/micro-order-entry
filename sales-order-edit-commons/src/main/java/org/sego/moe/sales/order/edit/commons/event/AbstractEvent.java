package org.sego.moe.sales.order.edit.commons.event;

public class AbstractEvent {

	private String id;

	public AbstractEvent() {
	}

	public AbstractEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
