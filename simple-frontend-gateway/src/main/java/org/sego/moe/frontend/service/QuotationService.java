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

import org.sego.moe.commons.model.CardEvent;
import org.sego.moe.commons.model.OrderItemChange;
import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
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
	public void receiveMessage(CardEvent message) {
		System.out.println("receiveMessage: " + message);
		applyEvent(message);
	}

	public SalesOrder getSalesOrder(Long id) {
		return storage.get(id);
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

		CardEvent event = new CardEvent();
		event.setCardId(id);
		event.setOrderItems(Collections.singletonList(oi));
		restTemplate.postForEntity("http://shopping-cart-service/v1/events", event, String.class);
	}

	private void applyEvent(CardEvent message) {
		SalesOrder card = storage.putIfAbsent(message.getCardId(), new SalesOrder());
		Lock lock = lockRegistry.obtain(card);
		try {
			lock.lock();
			card.setId(message.getCardId().toString());
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
					System.out.println("ois: " + ois);
					if (!ois.isEmpty()) {
						if (t.getOrderItems() == null) {
							t.setOrderItems(new CopyOnWriteArrayList<>());
						}
						t.getOrderItems().addAll(ois);
					}
				}
			});
		} finally {
			lock.unlock();
		}
	}

	private OrderItem mapOrderItem(OrderItemChange oi) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(oi.getId());
		orderItem.setOfferId(oi.getOfferId().toString());
		orderItem.setName("Offer_" + oi.getOfferId());
		orderItem.setQuantity((Integer) oi.getAttributes().get(OrderItemChange.QUANTITY));
		orderItem.setParentId((String) oi.getAttributes().get(OrderItemChange.PARENT_OI));
		return orderItem;
	}
}