package org.sego.moe.frontend.service;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.sego.moe.frontend.FrontendConfig;
import org.sego.moe.frontend.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CatalogService {

	@Autowired
	private FrontendConfig config;

	@Autowired
	private WebClient webClient;

	@Cacheable("offer")
	public Mono<Offer> getOffer(UUID offerId) {
		System.out.println(config.getCatalog());
		return webClient.get().uri(config.getCatalog() + "/v1/offer/" + offerId).accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange().log().flatMap(rsp -> rsp.bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
				})).map(new Function<Map<String, String>, Offer>() {
					@Override
					public Offer apply(Map<String, String> offerMap) {
						Offer offer = new Offer();
						offer.setId(offerId);
						offer.setMass(Integer.valueOf((String) offerMap.get("mass")).intValue());
						offer.setName((String) offerMap.get("name"));
						offer.setDescription((String) offerMap.get("description"));
						System.out.println(offer);
						return offer;
					}
				}).cache();
	}

}
