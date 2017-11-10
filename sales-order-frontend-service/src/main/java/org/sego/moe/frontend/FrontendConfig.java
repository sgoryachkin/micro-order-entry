package org.sego.moe.frontend;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="frontend")
public class FrontendConfig {

	@NotBlank
    private String eventstore;

	@NotBlank
	private String catalog;

	public String getEventstore() {
		return eventstore;
	}

	public void setEventstore(String eventstore) {
		this.eventstore = eventstore;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	
}