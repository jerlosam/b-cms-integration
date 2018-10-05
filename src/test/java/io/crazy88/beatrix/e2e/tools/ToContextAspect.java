package io.crazy88.beatrix.e2e.tools;

import internal.katana.core.reporter.Reporter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jbehave.core.annotations.ToContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

@Aspect
@TestComponent
public class ToContextAspect {

    @Autowired
    private Reporter reporter;

    @AfterReturning(pointcut = "execution(@org.jbehave.core.annotations.ToContext * *(..))",returning = "result")
    public void reportAfterAddToContext(JoinPoint joinPoint, Object result){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String contextValue = signature.getMethod().getAnnotation(ToContext.class).value();
        reporter.info(String.format("Added to the context (as %s) : %s",contextValue, result));
    }

}
