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

package org.sergei.auth.authorization;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.auth.AuthApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@AutoConfigureMockMvc
public class OAuthTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthTest.class);

    private static final String RESOURCE_URL = "http://localhost";
    private static final String OAUTH_ENDPOINT = "/oauth/token";
    private static final String GATEWAY_URL = "https://localhost:9090";
    private static final String RESERVATION_RES_PATH = "/reservation-rest";
    private static final String CUSTOMERS_PATH = "/customers";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get(GATEWAY_URL + RESERVATION_RES_PATH + CUSTOMERS_PATH + "/1"))
                .andExpect(status().isUnauthorized());
    }

    @Ignore
    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        String accessToken = getAccessToken("testusername", "testpassword");
        LOGGER.debug("Access token is: {}", accessToken);
        mockMvc.perform(get(GATEWAY_URL + RESERVATION_RES_PATH + CUSTOMERS_PATH + "/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isForbidden());
    }

    @Ignore
    @Test
    public void obtainAccessToken() throws Exception {
        Assert.assertEquals(HttpStatus.OK, getAccessToken("admin", "123456"));
    }

    private String getAccessToken(String username, String password) throws Exception {
        return mockMvc.perform(
                post(RESOURCE_URL + OAUTH_ENDPOINT)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding("UTF-8")
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password")
                        .param("scope", "read write trust")
                        .param("client_id", "trusted-client")
                        .param("client_secret", "trusted-client-secret"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.access_token", notNullValue()))
                .andExpect(jsonPath("$.token_type", equalTo("bearer")))
                .andExpect(jsonPath("$.refresh_token", notNullValue()))
                .andExpect(jsonPath("$.expires_in", greaterThan(4000)))
                .andExpect(jsonPath("$.scope", equalTo("read write trust")))
                .andReturn().getResponse().getContentAsString();
    }
}
