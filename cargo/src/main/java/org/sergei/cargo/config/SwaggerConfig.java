package org.sergei.cargo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.sergei.cargo.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiMetaData());
    }

    /**
     * API information
     *
     * @return ApiInfo object
     */
    private ApiInfo apiMetaData() {
        return new ApiInfo(
                "Cargo flight management service",
                "Cargo movement management service, receive, validate and proceed",
                "1.0", null, null, null, null, List.of());
    }
}
