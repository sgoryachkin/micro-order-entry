package org.sego.moe.dao;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesOrderMessageRepository {
	
	Mono<SalesOrderEditEvent> insert(SalesOrderEditEvent event);
	
	Flux<SalesOrderEditEvent> findBySalesOrderId(Long cardId);

}
