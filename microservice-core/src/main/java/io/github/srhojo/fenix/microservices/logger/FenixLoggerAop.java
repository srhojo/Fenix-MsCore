package io.github.srhojo.fenix.microservices.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.srhojo.fenix.microservices.exceptions.FenixException;

/**
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 */
@Aspect
@Component
public class FenixLoggerAop {

    private static final String HTTP_EXCEPTION = "Warehouse AOP: Handled exception: [ HttpCode: {}, Code: {}, Detail: {} ].";
    private static final String UNHANDLED_EXCEPTION = "Warehouse AOP: Unhandled exception: {}.";
    private static final String BEFORE_AOP = "Warehouse AOP Before: Executing:[ {} ].";
    private static final String ELAPSED_TIME = "Warehouse AOP: Method executed:[ {} ], elapsed time: {} ms";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@io.github.srhojo.fenix.microservices.logger.FenixLogger *)")
    public void markedAsLogged() {
        // Do nothing.
    }

    @Before("markedAsLogged()")
    public void logBefore(final JoinPoint joinPoint) {
        logger.info(BEFORE_AOP, joinPoint.getSignature());
    }

    @Around("markedAsLogged()")
    public Object executionAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final long startMillis = System.currentTimeMillis();
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (final FenixException se) {
            logger.error(HTTP_EXCEPTION, se.getStatus(), se.getCode(), se.getDetails());
            throw se;
        } catch (final Exception e) {
            logger.error(UNHANDLED_EXCEPTION, e.getMessage());
            throw new FenixException(e);
        } finally {
            logger.info(ELAPSED_TIME, joinPoint.getSignature(), System.currentTimeMillis() - startMillis);
        }
        return proceed;
    }
}
