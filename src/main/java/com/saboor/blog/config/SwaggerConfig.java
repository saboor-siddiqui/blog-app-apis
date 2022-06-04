package com.saboor.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys(){
        return  new ApiKey("JWT",AUTHORIZATION_HEADER,"Header");
    }

    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences(){

        AuthorizationScope scopes = new AuthorizationScope("global","Access Everything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scopes}));
    }

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo())
                                                      .securityContexts(securityContexts())
                                                      .securitySchemes(Arrays.asList(apiKeys()))
                                                      .select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Blogging Application : BackEnd Course","This project is developed by Saboor Siddiqui",
                           "1.0.0","Terms Of Service : None",new Contact("Saboor","https://www.saboorsiddiqui.com/","saboorsiddiqui0@gmail.com"),
                           "License Of APIs","API LICENSE URL", Collections.emptyList());
    }
}
