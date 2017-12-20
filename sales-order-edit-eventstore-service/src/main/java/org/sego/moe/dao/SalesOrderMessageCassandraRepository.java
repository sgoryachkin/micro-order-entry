package org.sego.moe.dao;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Function;

import org.sego.moe.dao.cassandra.model.EventData;
import org.sego.moe.dao.cassandra.model.EventDataKey;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SalesOrderMessageCassandraRepository implements SalesOrderMessageRepository {

	@Autowired
	ReactiveCassandraOperations cassandraOperations;

	@Override
	public Mono<SalesOrderEditEvent> insert(SalesOrderEditEvent event) {
		return cassandraOperations.insert(CONVERT_TO_DATA.apply(event)).map(data -> event);
	}

	@Override
	public Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId) {
		return findBySalesOrderId(cardId, 0);
	}

	@Override
	public Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId, long fromTime) {
		Select select = QueryBuilder.select().all().from(EventData.EVENT_DATA_TABLE_NAME);
		select.where(QueryBuilder.eq(EventDataKey.SALES_ORDER_COLUMN_NAME, cardId)).and(QueryBuilder.gt(EventDataKey.EVENT_TIME_COLUMN_NAME, fromTime));
		return cassandraOperations.select(select, EventData.class).map(CONVERT_FROM_DATA);
	}

	@Override
	public Mono<Long> countBySalesOrderId(UUID cardId) {
		return countBySalesOrderId(cardId, 0);
	}

	@Override
	public Mono<Long> countBySalesOrderId(UUID cardId, long fromTime) {
		Select select = QueryBuilder.select().countAll().from(EventData.EVENT_DATA_TABLE_NAME);
		select.where(QueryBuilder.eq(EventDataKey.SALES_ORDER_COLUMN_NAME, cardId)).and(QueryBuilder.gt(EventDataKey.EVENT_TIME_COLUMN_NAME, fromTime));
		return cassandraOperations.selectOne(select, Long.class);
	}
	
	private static final Function<EventData, SalesOrderEditEvent> CONVERT_FROM_DATA = new Function<EventData, SalesOrderEditEvent>() {
		@Override
		public SalesOrderEditEvent apply(EventData t) {
			return deserializeFromString(t.getBody());
		}
		
		private SalesOrderEditEvent deserializeFromString(String s) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(s, SalesOrderEditEvent.class);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	};
	
	private static final Function<SalesOrderEditEvent, EventData> CONVERT_TO_DATA = new Function<SalesOrderEditEvent, EventData>() {

		@Override
		public EventData apply(SalesOrderEditEvent t) {
			EventDataKey key = new EventDataKey(t.getSalesOrderId(), t.getRecordTime());
			EventData eventData = new EventData();
			eventData.setId(key);
			eventData.setBody(serializeToString(t));
			return eventData;
		}
		
		private String serializeToString(SalesOrderEditEvent event) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.writeValueAsString(event);
			} catch (JsonProcessingException e) {
				throw new IllegalArgumentException(e);
			}
		}

	};

}
