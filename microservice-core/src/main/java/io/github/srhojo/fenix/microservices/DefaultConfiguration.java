package io.github.srhojo.fenix.microservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 */
@Configuration
@EnableSwagger2
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "io.github.srhojo.fenix.*")
public class DefaultConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*"))).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedOrigins("http://localhost:8080")
                        .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Accept", "X-Requested-With",
                                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin")
                        .exposedHeaders("Access-Control-Expose-Headers", "Authorization", "Cache-Control",
                                "Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                                "Origin");

            }
        };
    }
}
