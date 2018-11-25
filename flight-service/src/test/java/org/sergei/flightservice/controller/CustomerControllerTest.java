package org.sergei.flightservice.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.flightservice.test.config.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky, 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "spring.cloud.config.discovery.enabled=false"})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebSecurityConfig.class})
//@EnableJpaRepositories(basePackages = "org.sergei.flightservice.repository")
//@EntityScan(basePackages = "org.sergei.flightservice.model")
@ActiveProfiles("test")
public class CustomerControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerTest.class);

    /*@Autowired
    @Qualifier("customerService")
    private CustomerService customerService;*/

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllCustomers() throws Exception {
//        CustomerDTO firstCustomer = new CustomerDTO(1L, "TestName", "Test last name", 45);
//        customerService.save(firstCustomer);
        String accessToken = getAccessToken("admin", "123456");
        LOGGER.debug("Access token is: {}", accessToken);
        JSONObject jsonObject = new JSONObject()
                .put("customerId", "customerId")
                .put("firstName", "firstName")
                .put("lastName", "lastName")
                .put("age", "age");
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8083/flight-api/v1/customers")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$..firstName").value(containsInAnyOrder("TestName")))
                .andExpect(jsonPath("$..lastName").value(containsInAnyOrder("Test last name")));
    }

    private String getAccessToken(String username, String password) throws Exception {
        return mockMvc.perform(
                post("http://localhost:8080/auth-api/oauth/token")
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
