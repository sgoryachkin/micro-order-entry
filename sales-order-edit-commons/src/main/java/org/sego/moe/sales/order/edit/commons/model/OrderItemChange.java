package org.sego.moe.sales.order.edit.commons.model;

import java.util.Map;

public class OrderItemChange {
	
	public static final String TOP_PARENT_ID = "top";
	
	private String id;
	private Long offerId;
	private int quantity;
	private String parentId;
	
	private Map<Long, Object> attributes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	public Map<Long, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<Long, Object> attributes) {
		this.attributes = attributes;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
