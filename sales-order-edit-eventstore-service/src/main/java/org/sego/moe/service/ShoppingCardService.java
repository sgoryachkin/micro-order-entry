package org.sego.moe.service;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

import reactor.core.publisher.Flux;

public interface ShoppingCardService {
	
	void addEditSalesOrderEvent(SalesOrderEditEvent cartEvent);
	
	Flux<SalesOrderEditEvent> getSalesOrderEvents(Long salesOrderId);

}
