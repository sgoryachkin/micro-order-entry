package org.sego.moe.frontend.service;

import java.util.Map;

import org.sego.moe.frontend.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogService {
	
	@Autowired
	protected RestTemplate restTemplate;
	
    @Cacheable("offer")
	public Offer getOffer(Long offerId) {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> offerMap = restTemplate.getForEntity("http://localhost:8881/v1/offer/" + offerId, Map.class);
		Offer offer = new Offer();
		offer.setMass(Integer.valueOf((String) offerMap.getBody().get("mass")).intValue());
		offer.setName((String) offerMap.getBody().get("name"));
		offer.setDescription((String) offerMap.getBody().get("description"));
		return offer;
	}

}
