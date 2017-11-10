package org.sego.moe.catalog.api.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = { "/v1" })
public class SalesCatalogControllerV1 {

	@Autowired
	protected WebClient webClient;

	@GetMapping(path = "/offer/{offerId}")
	//@Cacheable("offer")
	public Mono<Map<String, String>> getOffer(@PathVariable Long offerId) {
		String url = "https://swapi.co/api/people/" + offerId + "?format=json";
		System.out.println("Offer request: " + offerId);
		Mono<Map<String, String>> rsp = webClient.get().uri(url).accept(MediaType.APPLICATION_JSON_UTF8)
				.header("User-Agent", "Chrome").exchange().log().flatMap(r -> r.bodyToMono(Map.class)).map(map -> mapResponse(map));
		return rsp;
	}

	private Map<String, String> mapResponse(Map<?, ?> rsp) {
		Map<String, String> offer = new HashMap<>();
		offer.put("name", (String) rsp.get("name"));
		offer.put("description", (String) rsp.get("url"));
		offer.put("mass", (String) rsp.get("mass"));
		return offer;
	}

}