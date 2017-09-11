package org.sego.moe.frontend.model;

import java.util.List;

public class OrderItem {
	
	private String id;
	private String name;
	private Integer quontity;
	private List<OrderItem> orderItems;
	
	
	
	public OrderItem() {
		super();
	}

	public OrderItem(String id, String name, Integer quontity) {
		super();
		this.id = id;
		this.name = name;
		this.quontity = quontity;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuontity() {
		return quontity;
	}
	public void setQuontity(Integer quontity) {
		this.quontity = quontity;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
}
