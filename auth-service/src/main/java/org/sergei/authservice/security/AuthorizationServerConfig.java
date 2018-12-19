package org.sergei.authservice.security;

import org.sergei.authservice.service.ApiUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * @author Sergei Visotsky
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final UserApprovalHandler userApprovalHandler;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtTokenEnhancer;
    private final ApiUserDetailsService apiUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServerConfig(DataSource dataSource, UserApprovalHandler userApprovalHandler,
                                     @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                     TokenStore tokenStore, JwtAccessTokenConverter jwtTokenEnhancer,
                                     ApiUserDetailsService apiUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
        this.userApprovalHandler = userApprovalHandler;
        this.tokenStore = tokenStore;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.apiUserDetailsService = apiUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // All the clients are stored into the database
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LOGGER.debug("User management with encoded password");
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .reuseRefreshTokens(false)
                .tokenEnhancer(jwtTokenEnhancer)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(apiUserDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.realm("API_REALM").passwordEncoder(passwordEncoder);
    }
}