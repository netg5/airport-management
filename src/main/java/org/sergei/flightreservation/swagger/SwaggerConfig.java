/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*@Value("${config.oauth2.accessTokenUri}")
    private String authServer;
    private static final String CLIENT_SECRET = "client_secret";*/

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.sergei.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                /*.securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))*/
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Flight reservation REST API")
                .description("Methods offered by this REST API")
                .version("1.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .contact(new Contact("", "", "sergei.visotsky@gmail.com"))
                .build();
    }

    /*@Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator("")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securitySchema() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "write all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(authServer);
        grantTypes.add(grantType);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**"))
                .build();
    }*/
}
