package io.github.srhojo.fenix.microservices;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@EnableHystrix
@EnableEurekaClient
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "io.github.srhojo.fenix.*.clients")
@Import({ DefaultConfiguration.class })
@SpringBootApplication
public @interface FenixMicroservice {

}
