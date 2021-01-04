package com.ayprojects.helpinghands.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;
    @Value("${oauth.tokenTimeout}")
    private int expiration;

    @Value("${user.oauth.redirectUris}")
    private String redirectUri;

    @Value("${user.oauth.clientId}")
    private String clientId;

    @Value("${user.oauth.clientSecret}")
    private String clientSecret;


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.pathMapping("/oauth/token","/api/v1/users/login");
        endpoints.tokenStore(tokenStore).authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService);

        /*endpoints.authenticationManager(authenticationManager).tokenStore(jwtTokenStore())
                 .accessTokenConverter(tokenEnhancer());*/
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LOGGER.info("clientId=" + clientId);
        LOGGER.info("clientSecreteId=" + clientSecret);
        LOGGER.info("expiration=" + expiration);
        clients.inMemory().withClient(clientId).authorizedGrantTypes("password", "refresh_token")
                .authorities("USER").scopes("read", "write").accessTokenValiditySeconds(expiration)
                .refreshTokenValiditySeconds(expiration)
                .resourceIds(RestApiResourceServerConfiguration.RESOURCE_ID)
                .secret(bCryptPasswordEncoder.encode(clientSecret));
//                .redirectUris(redirectUri);
    }

}

