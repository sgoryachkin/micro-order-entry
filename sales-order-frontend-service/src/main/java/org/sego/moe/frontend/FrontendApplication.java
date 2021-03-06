package org.sego.moe.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableCaching
public class FrontendApplication {
	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
	
    @Bean
    WebClient webClient() {
/*        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(new Consumer<HttpClientOptions.Builder>() {
                    @Override
                    public void accept(HttpClientOptions.Builder builder) {
                        builder.disablePool();
                    }
                })).build();*/
        return WebClient.create();
    }
    
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
}
