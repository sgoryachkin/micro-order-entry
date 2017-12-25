package org.sego.moe.sales.order.edit.commons.event;

import java.util.Map;

public class SalesOrderEditEvent extends AbstractEvent {
	
	private Map<Long, Object> attributes;

	
	public SalesOrderEditEvent() {
		super();
	}

	public SalesOrderEditEvent(String id, Map<Long, Object> attributes) {
		super(id);
		this.attributes = attributes;
	}

	public Map<Long, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<Long, Object> attributes) {
		this.attributes = attributes;
	}
	
	
	

}
