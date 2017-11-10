package org.sego.moe.catalog;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.ipc.netty.http.client.HttpClientOptions;



//@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class CatalogApplication {
	
	Logger log = LoggerFactory.getLogger(CatalogApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

    @Bean
    WebClient webClient() {
    	log.info("Create WebClient");
        //return WebClient.create();
        return WebClient.builder().filter(new ExchangeFilterFunction() {
			
			@Override
			public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction next) {
		           log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
		           clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
		           Mono<ClientResponse> rsp = next.exchange(clientRequest);
		           rsp.log().subscribe(new Consumer<ClientResponse>() {

						@Override
						public void accept(ClientResponse t) {
							t.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
							t.bodyToMono(Map.class).log().doOnEach(new Consumer<Signal<Map>>() {
								@Override
								public void accept(Signal<Map> t) {
									log.info(String.valueOf(t.get()));
									
								}
							}).subscribe();
						}
		           });
		           return rsp;
			}
		}).build();
    }
}
