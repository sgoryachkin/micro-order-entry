package org.sego.moe.catalog.api.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(path = "/v1")
public class SalesCatalogControllerV1 {
	
    @Autowired                            
    protected RestTemplate restTemplate; 
    
    @GetMapping(path = "/offer/{offerId}")
    @Cacheable("offer")
    public Map<String, String> getOffer(@PathVariable Long offerId) {
    	Map<String, String> offer = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "firefox");

        HttpEntity<?> payload = new HttpEntity<>(headers);
    	
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> rsp = restTemplate.exchange("http://swapi.co/api/people/" + offerId + "/?format=json", HttpMethod.GET, payload, Map.class);
    	offer.put("name", (String) rsp.getBody().get("name"));
    	offer.put("description", (String) rsp.getBody().get("url"));
    	return offer;
    }

}