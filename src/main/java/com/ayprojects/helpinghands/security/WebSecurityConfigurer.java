package com.ayprojects.helpinghands.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import io.swagger.models.HttpMethod;


public class WebSecurityConfigurer {// extends WebSecurityConfigurerAdapter {

//    @Bean
//    public AuthenticationManager getAuthenticationManager() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    SecurityConfiguration securityConfiguration;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.userDetailsService(userDetailsService).passwordEncoder(securityConfiguration.passwordEncoder());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.ignoring().mvcMatchers(String.valueOf(HttpMethod.OPTIONS), "/**");
//        web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                //.and()
//                //uncomment above 2 lines if you want to use single user should access at a time
//                .authorizeRequests(configurer ->
//                        configurer
//                                .antMatchers(
//                                        "/api/v1/users/addUser",
//                                        "/api/v1/users/login",
//                                        "/swagger-ui.html"
//                                )
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated()
//                )
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//    }
}
