package org.sego.moe.dao;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SalesOrderMessageMongoRepository extends ReactiveMongoRepository<SalesOrderEditEvent, String>, SalesOrderMessageRepository {
	
	@Override
	Mono<SalesOrderEditEvent> insert(SalesOrderEditEvent event);
	
	Flux<SalesOrderEditEvent> findBySalesOrderId(Long cardId);

}
