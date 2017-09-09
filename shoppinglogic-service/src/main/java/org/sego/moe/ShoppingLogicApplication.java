package org.sego.moe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
@Configuration
public class ShoppingLogicApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingLogicApplication.class, args);
	}
	
	//@LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
}
