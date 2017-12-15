package org.sego.moe.service;

import java.util.UUID;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

import reactor.core.publisher.Flux;

public interface ShoppingCardService {
	
	void addEditSalesOrderEvent(SalesOrderEditEvent cartEvent);
	
	Flux<SalesOrderEditEvent> getSalesOrderEvents(UUID salesOrderId);
	
	Flux<SalesOrderEditEvent> getSalesOrderEvents(UUID salesOrderId, Long fromTime);

}
