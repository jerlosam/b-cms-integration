package io.crazy88.beatrix.e2e.tools;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;

@Aspect
@Component
public class RAFEnableAspect {
    @Autowired
    private E2EProperties properties;

    @Autowired
    private Reporter reporter;

    boolean isReferAFriendEnabled() {
        return !properties.getReferAFriendDisabled().contains(properties.getBrandCode());
    }

    @Around("@annotation(RAFEnable)")
    public Object executeIfRAFEnabled(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isReferAFriendEnabled()){
            reporter.info(String.format("Refer a friend tests are enabled for this brand"));
            return joinPoint.proceed();
        } else {
            reporter.info(String.format("Refer a friend tests are not enabled for this brand"));
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String returnType = method.getReturnType().getName();

        switch (returnType) {
            case "java.util.List":
                return new ArrayList<>();
            case "java.lang.String":
                return "";
            case "io.crazy88.beatrix.e2e.player.dto.PlayerSignup":
                return PlayerSignup.builder().build();
        }
        return new Object();
    }
}
