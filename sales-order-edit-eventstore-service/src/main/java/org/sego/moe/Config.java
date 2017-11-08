package org.sego.moe;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="eventstore")
public class Config {

	@NotBlank
    private List<String> consumer = new ArrayList<String>();

	public List<String> getConsumer() {
		return consumer;
	}

}