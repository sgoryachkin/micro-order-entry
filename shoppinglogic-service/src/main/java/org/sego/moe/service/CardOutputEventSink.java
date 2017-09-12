package org.sego.moe.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sego.moe.commons.model.CardEvent;
import org.sego.moe.commons.model.OrderItemChange;
import org.sego.moe.model.card.Card;
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
    public void receiveMessage(CardEvent message) {
        System.out.println("receiveMessage: " + message);
        applyEvent(message);
        // Rule
        int size = storage.get(message.getCardId()).getOfferIds().size();
        if (storage.get(message.getCardId()).getOfferIds().size() > 1) {
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
            	CardEvent ce = new CardEvent();
            	ce.setCardId(message.getCardId());
            	ce.setOrderItems(ois);
    	        ResponseEntity<String> rsp = restTemplate.postForEntity("http://shopping-cart-service/v1/events", ce, String.class);
    	        System.out.println(rsp.getBody());
        	}
        }
        System.out.println(storage);
    }
    
    private void applyEvent(CardEvent message) {
    	Card card = storage.putIfAbsent(message.getCardId(), new Card());
    	card.getOfferIds().addAll(message.getOrderItems().parallelStream().map(oi -> oi.getOfferId()).collect(Collectors.toSet()));
    }
}