package org.sego.moe.frontend.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.sego.moe.commons.model.CardEvent;
import org.sego.moe.commons.model.OrderItemChange;
import org.sego.moe.frontend.model.OrderItem;
import org.sego.moe.frontend.model.SalesOrder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@EnableBinding(CardSink.class)
@Component
public class CardOutputEventSink {

	Map<Long, SalesOrder> storage = new ConcurrentHashMap<>();

	@StreamListener(CardSink.INPUT)
	public void receiveMessage(CardEvent message) {
		System.out.println("receiveMessage: " + message);
		applyEvent(message);
	}
	
	public SalesOrder getSalesOrder(Long id) {
		return storage.get(id);
	}

	private void applyEvent(CardEvent message) {
		SalesOrder card = storage.putIfAbsent(message.getCardId(), new SalesOrder());
		card.setId(message.getCardId().toString());
		if (card.getOrderItems() == null) {
			card.setOrderItems(new CopyOnWriteArrayList<>());
		}
		List<OrderItem> ois = message
				.getOrderItems().stream().map(oic -> new OrderItem(String.valueOf(oic.getId()),
						"Offer_" + oic.getOfferId(), 1))
				.collect(Collectors.toList());
		
		card.getOrderItems().addAll(ois);

	}
}