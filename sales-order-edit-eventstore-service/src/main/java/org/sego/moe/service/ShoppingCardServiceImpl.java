package org.sego.moe.service;

import java.util.UUID;

import org.sego.moe.dao.SalesOrderMessageRepository;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCardServiceImpl.class);
	
	@Autowired
	CardOutputEventSource cardOutputEventSource;
	
	@Autowired
	SalesOrderMessageRepository messageRepository;
	
	@Override
	public void addEditSalesOrderEvent(SalesOrderEditEvent cartEvent) {
    	LOGGER.debug("addEditSalesOrderEvent: " + cartEvent.toString());
    	Mono<SalesOrderEditEvent> event = messageRepository.insert(cartEvent);
    	cardOutputEventSource.sendMessage(event).subscribe();
    }
    
	@Override
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(UUID salesOrderId) {
    	return messageRepository.findBySalesOrderId(salesOrderId);
    }

}
