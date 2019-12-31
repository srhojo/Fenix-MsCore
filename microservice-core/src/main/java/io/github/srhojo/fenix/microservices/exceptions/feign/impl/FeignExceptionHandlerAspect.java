package io.github.srhojo.fenix.microservices.exceptions.feign.impl;

import feign.FeignException;
import io.github.srhojo.fenix.microservices.exceptions.feign.ExceptionHandler;
import io.github.srhojo.fenix.microservices.exceptions.feign.FeignExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeignExceptionHandlerAspect implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Around("@annotation(feignExceptionHandler)")
    public Object handleException(final ProceedingJoinPoint joinPoint, final FeignExceptionHandler feignExceptionHandler) throws Throwable {
        logger.info("Entramos en el manejador de excepciones");
        Object rval = null;
        try {
            rval = joinPoint.proceed();
        } catch (final FeignException e) {

                final ExceptionHandler adapter = applicationContext.getBean(feignExceptionHandler.handler());
                final Boolean retry = feignExceptionHandler.retry();
                adapter.handler(joinPoint, retry, e);

        }
        return rval;
    }

}
