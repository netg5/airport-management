/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.config;

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

/**
 * @author Sergei Visotsky, 2018
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
//    private final UserService userService;

    private final UserApprovalHandler userApprovalHandler;

    // In case of storing data into DB should be removed
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServerConfig(/*DataSource dataSource,*/
            @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
            /*UserService userService*/UserApprovalHandler userApprovalHandler) {
//        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
//        this.userService = userService;
        this.userApprovalHandler = userApprovalHandler;
    }

    // All tokens are stored into the database
    /*@Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }*/

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
        clients.inMemory()
                .withClient("trusted-client")
                .secret(passwordEncoder.encode("trusted-client-secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .accessTokenValiditySeconds(259200)
                .refreshTokenValiditySeconds(2500000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(/*tokenStore()*/ tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
//                .userDetailsService(userService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.realm("CRM_REALM");
    }
}