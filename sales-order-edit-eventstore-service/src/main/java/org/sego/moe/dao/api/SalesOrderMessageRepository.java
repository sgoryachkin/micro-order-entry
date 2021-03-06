package org.sego.moe.dao.api;

import java.util.UUID;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesOrderMessageRepository {
	
	Mono<SalesOrderEditEvent> insert(SalesOrderEditEvent event);
	
	Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId);
	
	Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId, long fromTime);
	
	Mono<Long> countBySalesOrderId(UUID cardId);
	
	Mono<Long> countBySalesOrderId(UUID cardId, long fromTime);

}
