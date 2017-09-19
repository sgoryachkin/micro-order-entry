package org.sego.moe.dao;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

public interface SalesOrderMessageRepository {
	
	SalesOrderEditEvent save(SalesOrderEditEvent event);
	
	Iterable<SalesOrderEditEvent> findBySalesOrderId(Long cardId);

}
