package org.sergei.auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.sergei.auth.jpa.model.AuthUser;
import org.sergei.auth.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Component
@Slf4j
public class OAuth2TokenEnhancer implements TokenEnhancer {

    private final ApiUserService apiUserService;

    @Autowired
    public OAuth2TokenEnhancer(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    /**
     * Method to find user and enhance access token
     *
     * @param oAuth2AccessToken    access token
     * @param oAuth2Authentication authentication
     * @return user with set username and access token
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
                                     OAuth2Authentication oAuth2Authentication) {
        String username = oAuth2Authentication.getName();

        AuthUser authUser = apiUserService.findByUsername(username);
        log.debug("Found user is: {}", authUser.getUsername());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("username", authUser.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
