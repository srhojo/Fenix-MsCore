package io.github.srhojo.fenix.microservices.exceptions.feign;

import org.aspectj.lang.ProceedingJoinPoint;

@FunctionalInterface
public interface ExceptionHandler {

    Object handler(final ProceedingJoinPoint joinPoint, Boolean retry, final Object... args) throws Throwable;
}
