package org.sego.moe.frontend.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.sego.moe.frontend.model.Offer;
import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.sego.moe.sales.order.edit.commons.model.utils.OrderItemAttributeUtils;
import org.sego.moe.sales.order.edit.commons.model.utils.OrderItemChangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.ConnectableFlux;

@Service
public class QuotationService {

	Map<UUID, SalesOrder> storage = new ConcurrentHashMap<>();

	LockRegistry lockRegistry = new DefaultLockRegistry();
	
	@Autowired
	private WebClient webClient;

	//@Autowired
	//private CatalogService catalogService;

	public void receiveMessage(SalesOrderEditEvent message) {
		System.out.println("receiveMessage: " + message);
		applyEvent(message);
	}

	public SalesOrder getSalesOrder(UUID salesOrderId) {
		Lock lock = lockRegistry.obtain(salesOrderId);
		try {
			lock.lock();
			SalesOrder card = storage.get(salesOrderId);
			if (card == null || card.getEventCount() == 0) {
				storage.put(salesOrderId, restoreFromEvents(salesOrderId));
			}
			return storage.get(salesOrderId);
		} finally {
			lock.unlock();
		}
	}

	public void addOrderItem(UUID id, String parentOrderItemId, OrderItem orderItem) {
		System.out.println("addOrderItem");
		OrderItemChangeBuilder builder = new OrderItemChangeBuilder();
		builder.attribute(OrderItemChange.OFFER, orderItem.getOfferId());
		builder.attribute(OrderItemChange.QUANTITY, orderItem.getQuantity() == null ? 1 : orderItem.getQuantity());
		builder.parent(id);


		SalesOrderEditEvent event = new SalesOrderEditEvent();
		event.setSalesOrderId(id);
		event.setOrderItems(Collections.singletonList(builder.build()));
		System.out.println("Send: " + event);
		webClient.post().uri("http://localhost:8861/v1/events").syncBody(event).exchange().log().doFinally(cr -> System.out.println(cr)).subscribe(cr -> System.out.println("Successfuly send"));
	}

	private void applyEventToSalesOrder(SalesOrderEditEvent message, SalesOrder card) {
		System.out.println("applyEventToSalesOrder: " + message);
		card.setId(message.getSalesOrderId().toString());
		if (card.getOrderItems() == null) {
			card.setOrderItems(new CopyOnWriteArrayList<>());
		}
		card.setEventCount(card.getEventCount() + 1);
		List<OrderItem> ois = message.getOrderItems().stream().map(oic -> mapOrderItem(oic))
				.filter(oi -> oi.getParentId().equals(message.getSalesOrderId())).collect(Collectors.toList());
		card.getOrderItems().addAll(ois);

		card.getOrderItems().stream().forEach(new Consumer<OrderItem>() {

			@Override
			public void accept(OrderItem t) {
				List<OrderItem> ois = message.getOrderItems().stream().map(oic -> mapOrderItem(oic))
						.filter(oi -> t.getId().equals(oi.getParentId())).collect(Collectors.toList());
				if (!ois.isEmpty()) {
					if (t.getOrderItems() == null) {
						t.setOrderItems(new CopyOnWriteArrayList<>());
					}
					t.getOrderItems().addAll(ois);
				}
			}
		});
	}

	private void applyEvent(SalesOrderEditEvent message) {
		Lock lock = lockRegistry.obtain(message.getSalesOrderId());
		try {
			lock.lock();
			SalesOrder card = storage.get(message.getSalesOrderId());
			if (card == null || card.getEventCount() == 0) {
				storage.put(message.getSalesOrderId(), restoreFromEvents(message.getSalesOrderId()));
			} else {
				applyEventToSalesOrder(message, card);
			}
		} finally {
			lock.unlock();
		}
	}

	private SalesOrder restoreFromEvents(UUID id) {
		SalesOrder so = new SalesOrder();
		ConnectableFlux<SalesOrderEditEvent> events = webClient.get().uri("http://localhost:8861/v1/events/" + id).retrieve().bodyToFlux(SalesOrderEditEvent.class).log("restoreFromEvents").doFinally(st -> System.out.println(st)).publish();
		System.out.println("restoreFromEvents before block " + id);
		//events.autoConnect(1);
		events.subscribe(o -> applyEventToSalesOrder(o, so));
		events.connect();
		//events.
		System.out.println("Last: " + events.blockLast());
		
		//events.blockLast();
		
		
		//System.out.println("restoreFromEvents count: " + count);
		return so;
		
	}

	private OrderItem mapOrderItem(OrderItemChange oi) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(oi.getId());
		//UUID offerId = (UUID) OrderItemAttributeUtils.getAttrValue(oi, OrderItemChange.OFFER);
		orderItem.setOfferId((UUID.fromString((String) OrderItemAttributeUtils.getAttrValue(oi, OrderItemChange.OFFER))));
		orderItem.setQuantity((Integer) OrderItemAttributeUtils.getAttrValue(oi, OrderItemChange.QUANTITY));
		orderItem.setReason((String) OrderItemAttributeUtils.getAttrValue(oi, 10l));
		orderItem.setParentId(oi.getParentId());
/*		catalogService.getOffer(offerId).subscribe(new Consumer<Offer>() {
			@Override
			public void accept(Offer offer) {
				orderItem.setName(offer.getName());
				orderItem.setDescription(offer.getDescription());
			}
		});*/
		return orderItem;
	}
}