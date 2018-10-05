package io.crazy88.beatrix.e2e;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@ConfigurationProperties("e2e")
@EnableConfigurationProperties
public class E2ENonFunctionalProperties {
    String playerHomeUrl;
}
