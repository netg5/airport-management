/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthorizationServerConfig(DataSource dataSource,
                                     @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager/*,
                                     UserDetailsService userDetailsService*/) {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
    }

    // All tokens are stored into the database
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients/*.inMemory()*/
                .jdbc(dataSource)
                .withClient("trusted-client")
                .secret("trusted-client-secret")
                .scopes("read, write, trust")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .autoApprove(true)
                .and()
                .withClient("admin")
                .accessTokenValiditySeconds(259200)
                .refreshTokenValiditySeconds(2500000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
//                .userDetailsService(userDetailsService);
    }

}