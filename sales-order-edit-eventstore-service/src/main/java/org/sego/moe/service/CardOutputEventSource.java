package org.sego.moe.service;

import java.util.stream.Collectors;

import org.sego.moe.Config;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CardOutputEventSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardOutputEventSource.class);

	@Autowired
	private Config config;

	@Autowired
	private WebClient webClient;

	public Mono<Void> sendMessage(Mono<SalesOrderEditEvent> message) {
		Mono<SalesOrderEditEvent> cacheMessage = message.cache();
		return Mono.when(config.getConsumer().stream()
				.map(t -> webClient.post().uri(t).accept(MediaType.APPLICATION_JSON_UTF8)
						.body(cacheMessage, SalesOrderEditEvent.class).exchange().doOnSuccess(rsp -> LOGGER.debug("Send to " + rsp.headers().asHttpHeaders().getLocation())))
				.collect(Collectors.toList())).doOnSuccess(v -> LOGGER.debug("All message was send"));

	}
}