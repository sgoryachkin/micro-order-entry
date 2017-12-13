package org.sego.moe.sales.order.edit.commons.model.utils;

import org.sego.moe.sales.order.edit.commons.model.OrderItemChange;

public class OrderItemAttributeUtils {

	public static Object getAttrValue(OrderItemChange orderItemChange, Long attrId) {
		if (orderItemChange.getAttributes() == null) {
			return null;
		} else {
			return orderItemChange.getAttributes().get(attrId);
		}
	}

	public static Object getAttrValue(OrderItemChange orderItemChange, Long attrId, Object defaultValue) {
		Object result = getAttrValue(orderItemChange, attrId);
		return result != null ? result : defaultValue;
	}
}
