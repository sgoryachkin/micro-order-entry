package org.sego.moe.sales.order.edit.commons.model;

import java.util.List;
import java.util.Set;

public class SalesOrderEditEvent {

	private String id;

	private Long salesOrderId;
	
	private String sourceEventId;

	private int checkSumm;

	// Data

	private List<OrderItemChange> orderItems;

	private Set<String> orderItemsDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Set<String> getOrderItemsDelete() {
		return orderItemsDelete;
	}

	public void setOrderItemsDelete(Set<String> orderItemsDelete) {
		this.orderItemsDelete = orderItemsDelete;
	}

	public int getCheckSumm() {
		return checkSumm;
	}

	public void setCheckSumm(int checkSumm) {
		this.checkSumm = checkSumm;
	}
	public String getSourceEventId() {
		return sourceEventId;
	}

	public void setSourceEventId(String sourceEventId) {
		this.sourceEventId = sourceEventId;
	}

	@Override
	public String toString() {
		return "SalesOrderEditEvent [id=" + id + ", salesOrderId=" + salesOrderId + ", sourceEventId=" + sourceEventId
				+ ", checkSumm=" + checkSumm + ", orderItems=" + orderItems + ", orderItemsDelete=" + orderItemsDelete
				+ "]";
	}

}
