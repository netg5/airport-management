/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.authservice.config.security;

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

        User user = apiUserService.findByUsername(username);
        LOGGER.debug("Found user is: {}", user.getUsername());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("username", user.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
