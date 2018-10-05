package io.crazy88.beatrix.e2e.tools;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.BrandPropertiesClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Aspect
@TestComponent
public class ReactiveChatAspect {

    @Autowired
    private E2EProperties e2EProperties;

    @Autowired
    private BrandPropertiesClient brandPropertiesClient;

    private static final String CHAT_ENABLED_PROPERTY = "site.config.reactive.chat.enabled";

    @Autowired
    private Reporter reporter;

    boolean isChatEnabled() {
        Map<String, List<String>>
                properties = brandPropertiesClient.getProperties(e2EProperties.getBrandCode(), CHAT_ENABLED_PROPERTY);
        List<String> values = properties.get(CHAT_ENABLED_PROPERTY);
        if (values != null && values.size() > 0) {
            return Boolean.valueOf(values.get(0));
        }

        return false;
    }

    @Around("@annotation(ReactiveChatEnable)")
    public Object executeIfReactiveChatEnabled(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isChatEnabled()) {
            reporter.info(String.format("Reactive Chat tests are enabled for brand %s", e2EProperties.getBrandCode()));
            return joinPoint.proceed();
        } else {
            reporter.info(String.format("Reactive Chat tests are not enabled for brand %s", e2EProperties.getBrandCode()));
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String returnType = method.getReturnType().getName();

        switch (returnType) {
        case "java.util.List":
            return new ArrayList<>();
        case "java.lang.String":
            return "";
        }
        return new Object();
    }

}
