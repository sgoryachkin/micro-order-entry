package org.sego.moe.dao.impl.cassandra.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * @author sego1113
 *
 */
@PrimaryKeyClass
public class EventDataKey implements Serializable {
	
	private static final long serialVersionUID = 6081587564657965616L;
	
	public static final String SALES_ORDER_COLUMN_NAME = "salesOrderId";
	public static final String EVENT_TIME_COLUMN_NAME = "eventTime";

	@PrimaryKeyColumn(name=EventDataKey.SALES_ORDER_COLUMN_NAME, ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID salesOrderId;
		  
    @PrimaryKeyColumn(name=EventDataKey.EVENT_TIME_COLUMN_NAME, ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private long eventTime;

	public UUID getSalesOrderId() {
		return salesOrderId;
	}
	
	public EventDataKey() {
		super();
	}

	public EventDataKey(UUID salesOrderId, long eventTime) {
		super();
		this.salesOrderId = salesOrderId;
		this.eventTime = eventTime;
	}

	public void setSalesOrderId(UUID salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public long getEventTime() {
		return eventTime;
	}

	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventDataKey [");
		if (salesOrderId != null) {
			builder.append("salesOrderId=");
			builder.append(salesOrderId);
			builder.append(", ");
		}
		builder.append("eventTime=");
		builder.append(eventTime);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (eventTime ^ (eventTime >>> 32));
		result = prime * result + ((salesOrderId == null) ? 0 : salesOrderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EventDataKey other = (EventDataKey) obj;
		if (eventTime != other.eventTime) {
			return false;
		}
		if (salesOrderId == null) {
			if (other.salesOrderId != null) {
				return false;
			}
		} else if (!salesOrderId.equals(other.salesOrderId)) {
			return false;
		}
		return true;
	}


}
