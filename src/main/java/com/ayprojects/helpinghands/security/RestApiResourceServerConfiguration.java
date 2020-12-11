package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.AppConstants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class RestApiResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "restservice";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/","/api/v"+ AppConstants.CURRENT_API_VERSION+"/users/addUser","/api/v"+ AppConstants.CURRENT_API_VERSION+"/imageUpload/uploadUserImage",
                "/api/v"+ AppConstants.CURRENT_API_VERSION+"/users/getUserByMobile").permitAll()
                .antMatchers("/api/**").authenticated();
        /*http
                .cors()
                .and()
                .csrf().disable()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                //uncomment above 2 lines if you want to use single user should access at a time
                .authorizeRequests(configurer ->
                        configurer
                                .antMatchers(
                                        "/api/v1/users/addUser",
                                        "/api/v1/users/login"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);*/
    }



}