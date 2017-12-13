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
		return "SalesOrderEditEvent [id=" + id + ", parentId=" + parentId + ", chainId=" + chainId
				+ ", eventOriginatorId=" + eventOriginatorId + ", eventChainOriginatorId=" + eventChainOriginatorId
				+ ", eventNumber=" + eventNumber + ", eventNumberOfChain=" + eventNumberOfChain + ", salesOrderId="
				+ salesOrderId + ", orderItems=" + orderItems + ", orderItemsDelete=" + orderItemsDelete + "]";
	}

	
	//
	
}
