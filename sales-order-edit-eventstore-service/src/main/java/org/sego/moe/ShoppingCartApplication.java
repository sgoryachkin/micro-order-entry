package org.sego.moe;

import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@EnableAxon
@SpringBootApplication
//@Import(AxonConfiguration.class)
public class ShoppingCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}
}
