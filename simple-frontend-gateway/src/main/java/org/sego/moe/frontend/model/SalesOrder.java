package org.sego.moe.frontend.model;

import java.util.List;

public class SalesOrder {
	
	private String id;
	private List<OrderItem> orderItems;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
}
