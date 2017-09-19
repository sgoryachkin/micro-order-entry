package org.sego.moe.dao;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderMessageMongoRepository extends MongoRepository<SalesOrderEditEvent, String> , SalesOrderMessageRepository {
	
	Iterable<SalesOrderEditEvent> findBySalesOrderId(Long cardId);

}
