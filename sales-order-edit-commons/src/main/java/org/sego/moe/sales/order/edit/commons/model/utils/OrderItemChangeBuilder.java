package org.sego.moe.sales.order.edit.commons.model.utils;

import java.util.HashMap;
import java.util.UUID;

import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;

/**
 * @author sego1113
 *
 */
public class OrderItemChangeBuilder {

	private OrderItemChange orderItemChange = new OrderItemChange();

	public OrderItemChangeBuilder attribute(Long attrId, Object value) {
		if (orderItemChange.getAttributes() == null) {
			orderItemChange.setAttributes(new HashMap<>());
		}
		orderItemChange.getAttributes().put(attrId, value);
		return this;
	}

	public OrderItemChangeBuilder parent(UUID parentId) {
		orderItemChange.setParentId(parentId);
		return this;
	}

	public OrderItemChange build() {
		orderItemChange.setId(UUID.randomUUID());
		return orderItemChange;
	}

}
