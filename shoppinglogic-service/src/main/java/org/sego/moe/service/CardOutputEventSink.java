package org.sego.moe.service;

import org.sego.moe.model.CardEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@EnableBinding(CardSink.class)
public class CardOutputEventSink {
	
    @Autowired                            
    protected RestTemplate restTemplate; 


    @StreamListener(CardSink.INPUT)
    public void receiveMessage(CardEvent message) {
        System.out.println("receiveMessage: " + message);
        if (message.getCardId() == 0) {
	        message.setOfferId(101l);
	        message.setCardId(2l);
	        ResponseEntity<String> rsp = restTemplate.postForEntity("http://localhost:8861/v1/events", message, String.class);
	        System.out.println(rsp.getBody());
        }    
    }
}