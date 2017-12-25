package org.sego.moe.sales.order.edit.commons.event;

public class SalesOrderCreateEvent extends AbstractEvent {

	public SalesOrderCreateEvent() {
		super();
	}

	public SalesOrderCreateEvent(String id) {
		super(id);
	}

}
