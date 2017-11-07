package org.sego.moe.service;

import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CardOutputEventSource {

    @Autowired
    private WebClient webClient;

    public void sendMessage(Mono<SalesOrderEditEvent> message) {
    	Mono<SalesOrderEditEvent> cacheMessage = message.cache();
    	webClient.post().uri("http://localhost:8873/v1/events").accept(MediaType.APPLICATION_JSON_UTF8).body(cacheMessage, SalesOrderEditEvent.class).exchange().log().subscribe();
    	webClient.post().uri("http://localhost:8871/v1/events").accept(MediaType.APPLICATION_JSON_UTF8).body(cacheMessage, SalesOrderEditEvent.class).exchange().log().subscribe();
    }
}