package org.sego.moe.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.sego.moe.sales.order.edit.commons.model.SalesOrderEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SalesOrderMessageClickHouseRepository implements SalesOrderMessageRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS soEvents( " + "id String, " + "soId String, " + "body String)"
				+ " ENGINE = Memory");
	}

	@Override
	public Mono<SalesOrderEditEvent> insert(SalesOrderEditEvent event) {
		event.setId(UUID.randomUUID().toString());
		jdbcTemplate.update("INSERT INTO soEvents (id, soId, body) VALUES (?, ?, ?)",
				new Object[] { event.getId(), event.getSalesOrderId().toString(), toString(event) });
		return Mono.just(event);
	}

	@Override
	public Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId) {

		Publisher<SalesOrderEditEvent> p = new Publisher<SalesOrderEditEvent>() {
			@Override
			public void subscribe(Subscriber<? super SalesOrderEditEvent> subscriber) {
				subscriber.onSubscribe(new Subscription() {
					
					@Override
					public void request(long n) {
						//
					}
					
					@Override
					public void cancel() {
						// No cancel
					}
				});
				
				try {
					jdbcTemplate.query("SELECT id, soId, body FROM soEvents WHERE soId=?",
							new Object[] { cardId.toString() }, new RowCallbackHandler() {
								@Override
								public void processRow(ResultSet arg0) throws SQLException {
									subscriber.onNext(fromString(arg0.getString("body")));
									System.out.println("in progress");
								}
							});
				} catch (Exception e) {
					subscriber.onError(e);
				}
				subscriber.onComplete();
			}
		};

		return Flux.from(p);
	}

	@Override
	public Flux<SalesOrderEditEvent> findBySalesOrderId(UUID cardId, long fromTime) {
		// TODO Auto-generated method stub
		return null;
	}

	private String toString(SalesOrderEditEvent event) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}

	}

	private SalesOrderEditEvent fromString(String s) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(s, SalesOrderEditEvent.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
