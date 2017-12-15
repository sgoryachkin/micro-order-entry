package org.sego.moe.sales.order.edit.commons.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SalesOrderEditEvent {

	// EVENT META
	
	/**
	 * Event ID
	 */
	private String id;
	
	/**
	 * Parent Event ID
	 */
	private String parentId;
	
	/**
	 * ID of first event of Chain
	 */
	private UUID chainId;
	
	/**
	 * ID of first event of Chain
	 */
	private Long recordTime;
	
	/**
	 * Originator service of this event
	 */
	private String eventOriginatorId;
	
	/**
	 * Originator service of this of chain of events
	 */
	private String eventChainOriginatorId;

	/**
	 * Check summ of Context Root Id to apply this event (may be count of events)
	 */
	private int eventNumber;
	
	/**
	 * Check summ of Context Root Id to apply this event (may be count of events)
	 */
	private int eventNumberOfChain;
	
	// EVENT DATA
	
	/**
	 * Context Root Id
	 */
	private UUID salesOrderId;

	// Data

	private List<OrderItemChange> orderItems;

	private Set<String> orderItemsDelete;

	//
	
	public UUID getChainId() {
		return chainId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setChainId(UUID chainId) {
		this.chainId = chainId;
	}

	public String getEventOriginatorId() {
		return eventOriginatorId;
	}

	public void setEventOriginatorId(String eventOriginatorId) {
		this.eventOriginatorId = eventOriginatorId;
	}

	public String getEventChainOriginatorId() {
		return eventChainOriginatorId;
	}

	public void setEventChainOriginatorId(String eventChainOriginatorId) {
		this.eventChainOriginatorId = eventChainOriginatorId;
	}

	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}

	public int getEventNumberOfChain() {
		return eventNumberOfChain;
	}

	public void setEventNumberOfChain(int eventNumberOfChain) {
		this.eventNumberOfChain = eventNumberOfChain;
	}

	public UUID getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(UUID salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public List<OrderItemChange> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemChange> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<String> getOrderItemsDelete() {
		return orderItemsDelete;
	}

	public void setOrderItemsDelete(Set<String> orderItemsDelete) {
		this.orderItemsDelete = orderItemsDelete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SalesOrderEditEvent [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (parentId != null) {
			builder.append("parentId=");
			builder.append(parentId);
			builder.append(", ");
		}
		if (chainId != null) {
			builder.append("chainId=");
			builder.append(chainId);
			builder.append(", ");
		}
		if (recordTime != null) {
			builder.append("recordTime=");
			builder.append(recordTime);
			builder.append(", ");
		}
		if (eventOriginatorId != null) {
			builder.append("eventOriginatorId=");
			builder.append(eventOriginatorId);
			builder.append(", ");
		}
		if (eventChainOriginatorId != null) {
			builder.append("eventChainOriginatorId=");
			builder.append(eventChainOriginatorId);
			builder.append(", ");
		}
		builder.append("eventNumber=");
		builder.append(eventNumber);
		builder.append(", eventNumberOfChain=");
		builder.append(eventNumberOfChain);
		builder.append(", ");
		if (salesOrderId != null) {
			builder.append("salesOrderId=");
			builder.append(salesOrderId);
			builder.append(", ");
		}
		if (orderItems != null) {
			builder.append("orderItems=");
			builder.append(orderItems);
			builder.append(", ");
		}
		if (orderItemsDelete != null) {
			builder.append("orderItemsDelete=");
			builder.append(orderItemsDelete);
		}
		builder.append("]");
		return builder.toString();
	}

	public Long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	
	//
	
}
