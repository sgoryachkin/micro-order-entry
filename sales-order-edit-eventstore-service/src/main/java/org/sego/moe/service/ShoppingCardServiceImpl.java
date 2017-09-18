package org.sego.moe.service;

import org.sego.moe.dao.SalesOrderMessageRepository;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
	
	@Autowired
	CardOutputEventSource cardOutputEventSource;
	
	@Autowired
	SalesOrderMessageRepository messageRepository;
	
    public void addEditSalesOrderEvent(SalesOrderEditEvent cartEvent) {
    	SalesOrderEditEvent event = messageRepository.save(cartEvent);
    	cardOutputEventSource.sendMessage(event);
    }
    
    public Iterable<SalesOrderEditEvent> getSalesOrderEvents(Long salesOrderId) {
    	return messageRepository.findBySalesOrderId(salesOrderId);
    }

}
