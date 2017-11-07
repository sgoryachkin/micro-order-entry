package org.sego.moe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sego.moe.model.card.Card;
import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class CardOutputEventSink {

	LockRegistry lockRegistry = new DefaultLockRegistry();

	Map<Long, Card> storage = new ConcurrentHashMap<>();

	@Autowired
	private WebClient webClient;

	@Autowired
	private CatalogService catalogService;

	public void receiveMessage(SalesOrderEditEvent message) {
		System.out.println("receiveMessage: " + message);
		applyEvent(message);
		// Rule
		int size = storage.get(message.getSalesOrderId()).getCount();
		if (size > 1) {
			List<OrderItemChange> ois = message.getOrderItems().stream()
					.filter(oi -> catalogService.getOffer(oi.getOfferId()).getMass() > 50)
					.map(new Function<OrderItemChange, OrderItemChange>() {
						@Override
						public OrderItemChange apply(OrderItemChange t) {
							OrderItemChange oi = new OrderItemChange();
							oi.setId(UUID.randomUUID().toString());
							oi.setOfferId(5l);
							oi.setQuantity(1);
							oi.setParentId(t.getId());
							Map<Long, Object> attributes = new HashMap<>();
							attributes.put(10l, "It is added to parent because: SO Size (>1): " + size
									+ ", Offer mass (>50): " + catalogService.getOffer(t.getOfferId()).getMass());
							oi.setAttributes(attributes);
							return oi;
						}
					}).collect(Collectors.toList());
			if (!ois.isEmpty()) {
				SalesOrderEditEvent ce = new SalesOrderEditEvent();
				ce.setSalesOrderId(message.getSalesOrderId());
				ce.setOrderItems(ois);
				ce.setSourceEventId(message.getId());
				webClient.post().uri("http://localhost:8861/v1/events").syncBody(ce).exchange().doOnSuccess(r -> System.out.println(ce.toString()));
			}
		}
	}

	private void applyEventToSalesOrder(SalesOrderEditEvent message, Card card) {
		if (message.getOrderItems() != null) {
			card.setCount(card.getCount() + message.getOrderItems().size());
		}
		if (message.getOrderItemsDelete() != null) {
			card.setCount(card.getCount() - message.getOrderItemsDelete().size());
		}
	}

	private void applyEvent(SalesOrderEditEvent message) {
		Lock lock = lockRegistry.obtain(message.getSalesOrderId());
		try {
			lock.lock();
			Card card = storage.get(message.getSalesOrderId());
			if (card == null) {
				card = storage.computeIfAbsent(message.getSalesOrderId(), id -> restoreFromEvents(id));
			} else {
				applyEventToSalesOrder(message, card);
			}
		} finally {
			lock.unlock();
		}
	}

	private Card restoreFromEvents(Long id) {
		Card so = new Card();
		Flux<SalesOrderEditEvent> events = webClient.get().uri("http://localhost:8861/v1/events/" + id).retrieve().bodyToFlux(SalesOrderEditEvent.class);
		events.doOnEach(o -> applyEventToSalesOrder(o.get(), so));
		if (events.count().block() != 0) {
			return so;
		} else {
			return null;
		}
	}
}