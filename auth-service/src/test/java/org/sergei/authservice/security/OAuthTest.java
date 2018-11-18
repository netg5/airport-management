package org.sergei.authservice.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class OAuthTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("http://localhost:8080/flight-api/v1/customers")
                .param("id", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken("testusername", "testpassword");
        LOGGER.debug("Access token is: {}", accessToken);
        mockMvc.perform(get("http://localhost:8080/flight-api/v1/customers")
                .header("Authorization", "Bearer " + accessToken)
                .param("id", "1"))
                .andExpect(status().isForbidden());
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "trusted-client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("http://localhost:8080/auth-api/oauth/token")
                .params(params)
                .with(httpBasic("trusted-client", "trusted-client-secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        LOGGER.debug("Result string is: {}", resultString);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
