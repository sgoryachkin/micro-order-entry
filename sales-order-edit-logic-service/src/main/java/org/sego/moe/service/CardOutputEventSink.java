package org.sego.moe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sego.moe.model.card.Card;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@EnableBinding(CardSink.class)
public class CardOutputEventSink {
	
	Map<Long, Card> storage = new ConcurrentHashMap<>();
	
    @Autowired                            
    protected RestTemplate restTemplate; 


    @StreamListener(CardSink.INPUT)
    public void receiveMessage(SalesOrderEditEvent message) {
        System.out.println("receiveMessage: " + message);
        applyEvent(message);
        // Rule
        int size = storage.get(message.getSalesOrderId()).getOfferIds().size();
        if (storage.get(message.getSalesOrderId()).getOfferIds().size() > 1) {
        	List<OrderItemChange> ois = message.getOrderItems().stream().filter(oi -> oi.getOfferId().equals(33l)).map(new Function<OrderItemChange, OrderItemChange>() {
				@Override
				public OrderItemChange apply(OrderItemChange t) {
	            	OrderItemChange oi = new OrderItemChange();
	            	oi.setId(UUID.randomUUID().toString());
	            	oi.setOfferId(55l);
	            	Map<Long, Object> attributes = new HashMap<>();
	            	attributes.put(OrderItemChange.PARENT_OI, t.getId());
	            	attributes.put(OrderItemChange.QUANTITY, size);
	            	oi.setAttributes(attributes);
	            	return oi;
				}
			}).collect(Collectors.toList());
        	if (!ois.isEmpty()) {
        		SalesOrderEditEvent ce = new SalesOrderEditEvent();
            	ce.setSalesOrderId(message.getSalesOrderId());
            	ce.setOrderItems(ois);
    	        ResponseEntity<String> rsp = restTemplate.postForEntity("http://sales-order-edit-eventstore-service/v1/events", ce, String.class);
    	        System.out.println(rsp.getBody());
        	}
        }
        System.out.println(storage);
    }
    
    private void applyEvent(SalesOrderEditEvent message) {
    	Card card = storage.putIfAbsent(message.getSalesOrderId(), new Card());
    	card.getOfferIds().addAll(message.getOrderItems().parallelStream().map(oi -> oi.getOfferId()).collect(Collectors.toSet()));
    }
}