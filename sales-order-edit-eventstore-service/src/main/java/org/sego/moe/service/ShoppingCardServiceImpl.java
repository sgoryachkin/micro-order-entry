package org.sego.moe.service;

import org.sego.moe.dao.SalesOrderMessageRepository;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
	
	@Autowired
	CardOutputEventSource cardOutputEventSource;
	
	@Autowired
	SalesOrderMessageRepository messageRepository;
	
	@Override
	public void addEditSalesOrderEvent(SalesOrderEditEvent cartEvent) {
    	System.out.println("addEditSalesOrderEvent " + cartEvent.toString());
    	Mono<SalesOrderEditEvent> event = messageRepository.insert(cartEvent);
    	cardOutputEventSource.sendMessage(event);
    }
    
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(Long salesOrderId) {
    	return messageRepository.findBySalesOrderId(salesOrderId);
    }



}
