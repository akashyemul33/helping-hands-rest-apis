package com.ayprojects.helpinghands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class HelpingHandsApplication extends SpringBootServletInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelpingHandsApplication.class);
    private static Class<HelpingHandsApplication> applicationClass = HelpingHandsApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(HelpingHandsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(applicationClass);
    }


}
