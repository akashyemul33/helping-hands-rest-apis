package com.ayprojects.helpinghands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger.host.url}")
    private String hostUrl;


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).host(hostUrl).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo("Helping Hands REST API's", "API documentation for Helping Hands", "1.0", "Terms of Service",
                new Contact("Helping Hands", "", ""), "", "",new ArrayList<>());
    }
}
