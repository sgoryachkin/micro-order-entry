package org.sego.moe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

//@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class ShoppingLogicApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingLogicApplication.class, args);
	}
	
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	WebClient webClient() {
		return WebClient.create();
	}
	
}
