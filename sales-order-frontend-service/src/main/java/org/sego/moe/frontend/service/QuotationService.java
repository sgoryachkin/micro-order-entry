package org.sego.moe.frontend.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@EnableBinding(CardSink.class)
@Component
public class QuotationService {

	Map<Long, SalesOrder> storage = new ConcurrentHashMap<>();

	LockRegistry lockRegistry = new DefaultLockRegistry();

	@Autowired
	protected RestTemplate restTemplate;

	@StreamListener(CardSink.INPUT)
	public void receiveMessage(SalesOrderEditEvent message) {
		System.out.println("receiveMessage: " + message);
		applyEvent(message);
	}

	public SalesOrder getSalesOrder(Long salesOrderId) {
		return storage.computeIfAbsent(salesOrderId, id -> restoreFromEvents(id));
	}

	public void addOrderItem(Long id, String parentOrderItemId, OrderItem orderItem) {
		OrderItemChange oi = new OrderItemChange();
		oi.setOfferId(Long.valueOf(orderItem.getOfferId()));
		oi.setId(UUID.randomUUID().toString());
		Map<Long, Object> attributes = new HashMap<>();
		if (parentOrderItemId != null) {
			attributes.put(OrderItemChange.PARENT_OI, parentOrderItemId);
		}
		attributes.put(OrderItemChange.QUANTITY, orderItem.getQuantity() == null ? 1 : orderItem.getQuantity());
		oi.setAttributes(attributes);

		SalesOrderEditEvent event = new SalesOrderEditEvent();
		event.setSalesOrderId(id);
		event.setOrderItems(Collections.singletonList(oi));
		restTemplate.postForEntity("http://sales-order-edit-eventstore-service/v1/events", event, String.class);
	}

	private void applyEventToSalesOrder(SalesOrderEditEvent message, SalesOrder card) {
		card.setId(message.getSalesOrderId().toString());
		if (card.getOrderItems() == null) {
			card.setOrderItems(new CopyOnWriteArrayList<>());
		}

		List<OrderItem> ois = message.getOrderItems().stream().map(oic -> mapOrderItem(oic))
				.filter(oi -> oi.getParentId() == null).collect(Collectors.toList());
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
		SalesOrder card = storage.computeIfAbsent(message.getSalesOrderId(), id -> restoreFromEvents(id));
		Lock lock = lockRegistry.obtain(card);
		try {
			lock.lock();
			applyEventToSalesOrder(message, card);
		} finally {
			lock.unlock();
		}
	}

	private SalesOrder restoreFromEvents(Long id) {
		SalesOrder so = new SalesOrder();
		ResponseEntity<List<SalesOrderEditEvent>> rsp = restTemplate.exchange("http://sales-order-edit-eventstore-service/v1/events/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<SalesOrderEditEvent>>() {
        });
		System.out.println(rsp.getBody());
		rsp.getBody().forEach(o -> applyEventToSalesOrder((SalesOrderEditEvent) o, so));
		return so;
	}

	private OrderItem mapOrderItem(OrderItemChange oi) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(oi.getId());
		orderItem.setOfferId(oi.getOfferId().toString());
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> offer = restTemplate.getForEntity("http://sales-catalog-service/v1/offer/" + oi.getOfferId(), Map.class);
		orderItem.setName((String) offer.getBody().get("name"));
		orderItem.setDescription((String) offer.getBody().get("description"));
		orderItem.setQuantity((Integer) oi.getAttributes().get(OrderItemChange.QUANTITY));
		orderItem.setParentId((String) oi.getAttributes().get(OrderItemChange.PARENT_OI));
		return orderItem;
	}
}