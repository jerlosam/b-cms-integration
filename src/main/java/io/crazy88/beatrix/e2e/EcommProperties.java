package io.crazy88.beatrix.e2e;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("e2e.ecomm")
public class EcommProperties {

    @Value("${e2e.ecomm.qaLdap.user}")
    private String qaLdapUser;

    @Value("${e2e.ecomm.qaLdap.password}")
    private String qaLdapPassword;

    private String customerManagerUrl;

    @Value("${e2e.ecomm.routing.user}")
    private String routingUser;

    @Value("${e2e.ecomm.routing.password}")
    private String routingPassword;

    @Value("${e2e.ecomm.routing.url}")
    private String routingUrl;

    @Value("${e2e.ecomm.routing.authUrl}")
    private String routingAuthUrl;

    @Value("${e2e.ecomm.payoutservice.url}")
    private String payoutServiceConsoleUrl;
    
    @Value("${e2e.ecomm.payoutservice.user}")
    private String payoutsUser;

    @Value("${e2e.ecomm.payoutservice.password}")
    private String payoutsPassword;

    @Value("${e2e.ecomm.ecommapi.user}")
    private String ecommAPIUser;

    @Value("${e2e.ecomm.ecommapi.password}")
    private String ecommAPIPassword;

}
