package org.sergei.authservice.security;

import org.sergei.authservice.model.User;
import org.sergei.authservice.service.ApiUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OAuth2TokenEnhancer implements TokenEnhancer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private ApiUserService apiUserService;

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

        User user = apiUserService.findByUsername(username);
        LOGGER.debug("Found user is: {}", user.getUsername());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("username", user.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
