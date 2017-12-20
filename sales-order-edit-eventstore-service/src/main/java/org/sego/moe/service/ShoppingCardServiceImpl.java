package org.sego.moe.service;

import java.util.UUID;

import org.sego.moe.dao.api.SalesOrderMessageRepository;
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Write record: " + cartEvent.toString());
		}
		cartEvent.setRecordTime(System.currentTimeMillis());
    	Mono<SalesOrderEditEvent> event = messageRepository.insert(cartEvent);
    	event.doOnError(e -> LOGGER.error(e.getMessage(), e)).subscribe();
    	//cardOutputEventSource.sendMessage(event).doOnError(e -> LOGGER.error(e.getMessage(), e)).subscribe();
    }
    
	@Override
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(UUID salesOrderId) {
    	return messageRepository.findBySalesOrderId(salesOrderId);
    }
	
	@Override
    public Flux<SalesOrderEditEvent> getSalesOrderEvents(UUID salesOrderId, Long fromTime) {
    	return messageRepository.findBySalesOrderId(salesOrderId, fromTime);
    }

	@Override
	public Mono<Long> getCountSalesOrderEvents(UUID salesOrderId) {
		return messageRepository.countBySalesOrderId(salesOrderId);
	}

	@Override
	public Mono<Long> getCountSalesOrderEvents(UUID salesOrderId, Long fromTime) {
		return messageRepository.countBySalesOrderId(salesOrderId, fromTime);
	}

}
