package org.sego.moe.model;

import java.util.Map;

public class OrderItem {
	
	private Long id;
	private Long offerId;
	private Map<Long, Object> attributes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", offerId=" + offerId + ", attributes=" + attributes + "]";
	}

}
