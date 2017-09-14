package org.sego.moe.service;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;

public interface ShoppingCardService {
	
	void addCartEvent(SalesOrderEditEvent cartEvent);

}
