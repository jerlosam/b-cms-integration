package io.crazy88.beatrix.e2e;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("e2e.bulk.alice.backoffice")
public class AliceBackofficeProperties {
	
	private String urlBase;
	
	private String urlLogin;
	
	private String urlHome;

	private String profileId;
}
