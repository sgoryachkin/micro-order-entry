package org.sego.moe.dao;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderMessageRepository extends MongoRepository<SalesOrderEditEvent, String> {
	
	Iterable<SalesOrderEditEvent> findBySalesOrderId(Long cardId);

}
