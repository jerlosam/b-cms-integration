package io.crazy88.beatrix.e2e;

import io.crazy88.beatrix.e2e.clients.SignupClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@TestConfiguration
@ComponentScan
@EnableFeignClients(clients = SignupClient.class)
@EnableAspectJAutoProxy
public class E2EConfiguration {
}
