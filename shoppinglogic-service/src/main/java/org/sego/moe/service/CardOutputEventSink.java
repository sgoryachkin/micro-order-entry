package org.sego.moe.service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.sego.moe.model.CardEvent;
import org.sego.moe.model.OrderItem;
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
        if (storage.get(message.getCardId()).getOfferIds().size() > 1) {
        	if ((message.getOrderItems()!=null && !message.getOrderItems().isEmpty()) &&
        	message.getOrderItems().stream().anyMatch(oi -> oi.getOfferId().equals(33l))) {
            	OrderItem oi = new OrderItem();
            	oi.setOfferId(55l);
            	CardEvent ce = new CardEvent();
            	ce.setCardId(message.getCardId());
            	ce.setOrderItems(Collections.singletonList(oi));
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