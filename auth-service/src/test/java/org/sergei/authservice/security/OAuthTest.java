package org.sergei.authservice.security;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * @author Sergei Visotsky, 2018
 */
@Ignore
@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class OAuthTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("http://localhost:8080/flight-api/v1/customers")
                .param("id", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        String accessToken = getAccessToken("testusername", "testpassword");
        LOGGER.debug("Access token is: {}", accessToken);
        mockMvc.perform(get("http://localhost:8080/flight-api/v1/customers")
                .header("Authorization", "Bearer " + accessToken)
                .param("id", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void obtainAccessToken() throws Exception {
        Assert.assertEquals(getAccessToken("admin", "123456"), HttpStatus.OK);
    }

    private String getAccessToken(String username, String password) throws Exception {
        return mockMvc.perform(
                post("http://localhost:8082/auth-api/oauth/token")
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
