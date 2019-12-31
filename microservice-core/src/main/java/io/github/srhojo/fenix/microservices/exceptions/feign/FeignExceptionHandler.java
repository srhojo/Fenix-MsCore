package io.github.srhojo.fenix.microservices.exceptions.feign;


import io.github.srhojo.fenix.microservices.exceptions.feign.impl.FeignDefaultExceptionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignExceptionHandler {


    /**
     * Class that will handle the exception. By default:
     * "DefautlExceptionHandler.class": but a custom handler can be set, which will
     * extend from the class "ExceptionHandler".
     *
     * @return object extends ExceptionHandler
     */
    Class<? extends ExceptionHandler> handler() default FeignDefaultExceptionHandler.class;


    /**
     * It's indicate if annotation have to retried the service. By Default is FALSE.
     *
     * @return boolean
     */
    boolean retry() default false;
}
