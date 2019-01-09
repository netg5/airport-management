package org.sergei.authservice.config.security;

import org.sergei.authservice.service.ApiUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Permissions and restrictions and access_token issuing
 *
 * @author Sergei Visotsky
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;
    private final ApiUserDetailsService apiUserDetailsService;

    @Autowired
    public WebSecurityConfig(ClientDetailsService clientDetailsService,
                             ApiUserDetailsService apiUserDetailsService) {
        this.clientDetailsService = clientDetailsService;
        this.apiUserDetailsService = apiUserDetailsService;
    }

    /**
     * All users are stored into the database all passwords are encoded using method {@link #encoder()}
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception any kind of exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(apiUserDetailsService).passwordEncoder(encoder());
    }

    /**
     * Authentication manager
     *
     * @return authenticated user
     * @throws Exception any kind of exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Get JWT token from the token store {@link #jwtTokenEnhancer()}
     *
     * @return enhanced JWT token
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * Allows to use keypair located in .jks file
     *
     * @return converted key
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "secretKey".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    /**
     * Takes client details from {@link ClientDetailsService} and sets token
     *
     * @param tokenStore gets token store as a parameter
     * @return handler with token and user details set
     */
    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    /**
     * Token approval store
     *
     * @param tokenStore token to approve in token store
     * @return approved token
     */
    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    /**
     * Password encoder
     *
     * @return base64 encoded password
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Set to be accessible [/oauth/token] endpoint
     *
     * @param http to set permissions and restrictions
     * @throws Exception any kind of exception
     */
    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName("API_REALM");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/docs", "/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
    }
}