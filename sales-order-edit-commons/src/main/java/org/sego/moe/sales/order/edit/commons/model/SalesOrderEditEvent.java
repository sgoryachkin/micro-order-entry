package org.sego.moe.sales.order.edit.commons.model;

import java.util.List;
import java.util.Set;

public class SalesOrderEditEvent {
	
	private Long salesOrderId;
	
	private List<OrderItemChange> orderItems;
	
	private Set<Long> orderItemsDelete;
	
	public List<OrderItemChange> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemChange> orderItems) {
		this.orderItems = orderItems;
	}
	public Long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public Set<Long> getOrderItemsDelete() {
		return orderItemsDelete;
	}
	public void setOrderItemsDelete(Set<Long> orderItemsDelete) {
		this.orderItemsDelete = orderItemsDelete;
	}
	
	
	@Override
	public String toString() {
		return "CardEvent [cardId=" + salesOrderId + ", orderItems=" + orderItems + ", orderItemsDelete=" + orderItemsDelete
				+ "]";
	}
	
}
