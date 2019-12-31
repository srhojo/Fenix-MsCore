package io.github.srhojo.fenix.microservices.exceptions.feign.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.github.srhojo.fenix.microservices.exceptions.FenixException;
import io.github.srhojo.fenix.microservices.exceptions.feign.ExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FeignDefaultExceptionHandler implements ExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper om;


    public FeignDefaultExceptionHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public Object handler(ProceedingJoinPoint joinPoint, Boolean retry, Object... args) throws Throwable {
        final FeignException feignException = (FeignException) args[0];

        try {
            final FenixException fenixException = om.readValue(feignException.contentUTF8(), FenixException.class);
            throw fenixException;

        } catch (final IOException e) {
            logger.error("PARSE exception: {}", e.getMessage());
            throw feignException;
        }
    }

}
