package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.FlightServiceApplication;
import org.sergei.flightservice.test.config.AppConfigTest;
import org.sergei.flightservice.test.config.ResourceServerConfiguration;
import org.sergei.flightservice.test.config.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky
 * @since 11/24/2018
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightServiceApplication.class, properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.discovery.enabled=false",
        "security.oauth2.accessTokenUri=http://localhost:8080/auth-api/oauth/token",
        "spring.getaway.port=8080"
})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AppConfigTest.class, ResourceServerConfiguration.class, WebSecurityConfigTest.class})
//@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
//@EntityScan(basePackages = "org.sergei.flightservice.model")
public class CustomerControllerTest {

    /*@MockBean
    private CustomerService customerService;*/

    @Autowired
    private MockMvc mvc;

    @Test
    public void endpointAccessibilityTest() throws Exception {
        String accessToken = obtainAccessToken("admin", "123456");
        mvc.perform(
                get("/v1/customers")
//                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomers() throws Exception {
//        CustomerDTO firstCustomer = new CustomerDTO(1L, "TestName", "Test last name", 45);
//        customerService.save(firstCustomer);
        JSONObject jsonObject = new JSONObject()
                .put("customerId", "customerId")
                .put("firstName", "firstName")
                .put("lastName", "lastName")
                .put("age", "age");
        mvc.perform(
                get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$..firstName").value(containsInAnyOrder("TestName")))
                .andExpect(jsonPath("$..lastName").value(containsInAnyOrder("Test last name")));
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
//        params.add("client_id", "trusted-client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mvc.perform(
                post("http://localhost:8080/auth-api/oauth/token")
                        .params(params)
                        .with(httpBasic("trusted-client", "trusted-client-secret"))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
