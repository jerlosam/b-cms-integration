package io.crazy88.beatrix.e2e.clients;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import io.crazy88.beatrix.e2e.EcommProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
@AllArgsConstructor
public class InstrumentsFeignClientConfiguration {

    private EcommProperties ecommProperties;

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // This header will overwrite the default header (Content-Type=[application/json;charset=UTF-8])
                // which is generated by Feign since Ecomm-API doesn't support the header "Content-Type" with a charset.
                requestTemplate.header("Content-Type", "application/json");
            }
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(ecommProperties.getEcommAPIUser(),
                ecommProperties.getEcommAPIPassword());
    }
}
