package org.sego.moe.dao.cassandra.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = EventData.EVENT_DATA_TABLE_NAME)
public class EventData {
	
	public static final String EVENT_DATA_TABLE_NAME = "event_data";

	@PrimaryKey
	private EventDataKey id;
	
	@Column
	private String body;

	public EventDataKey getId() {
		return id;
	}

	public void setId(EventDataKey id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
