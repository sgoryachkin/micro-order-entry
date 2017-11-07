package org.sego.moe.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

//@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class CatalogApplication {
	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

    @Bean
    WebClient webClient() {
        return WebClient.create();
    }
}
