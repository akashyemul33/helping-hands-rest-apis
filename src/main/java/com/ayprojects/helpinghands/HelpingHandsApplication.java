package com.ayprojects.helpinghands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class HelpingHandsApplication extends SpringBootServletInitializer {

	public static final Logger LOGGER=LoggerFactory.getLogger(HelpingHandsApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(HelpingHandsApplication.class, args);
	}

	private static Class<HelpingHandsApplication> applicationClass = HelpingHandsApplication.class;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(applicationClass);
	}

}
