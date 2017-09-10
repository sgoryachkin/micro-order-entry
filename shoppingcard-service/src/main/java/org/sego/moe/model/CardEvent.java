package org.sego.moe.model;

import java.util.List;

public class CardEvent {
	
	private Long cardId;
	
	private List<OrderItem> orderItems;
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	
	@Override
	public String toString() {
		return "CardEvent [cardId=" + cardId + ", orderItems=" + orderItems + "]";
	}
	
	

}
