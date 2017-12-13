package org.sego.moe.sales.order.edit.commons.model;

import java.util.Map;
import java.util.UUID;

public class OrderItemChange {
	
	public static final Long ITEM_TYPE = 50l;
	public static final Long OFFER = 100l;
	public static final Long QUANTITY = 200l;
	
	private UUID id;
	private UUID parentId;
	private Map<Long, Object> attributes;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
	public Map<Long, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<Long, Object> attributes) {
		this.attributes = attributes;
	}
	
	
	@Override
	public String toString() {
		return "OrderItemChange [id=" + id + ", parentId=" + parentId + ", attributes=" + attributes + "]";
	}
	
	
}
